package htcc.employee.service.controller.payslip;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.payslip.EmployeePayslipResponse;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Api(tags = "API bảng lương", description = "API lấy danh sách bảng lương theo tháng cho nhân viên")
@RestController
@Log4j2
public class EmployeePayslipController {
    // TODO : implement this class

    private static final List<String> incomeCategories = Arrays.asList("Lương cơ bản", "Phụ cấp ăn trưa", "Thưởng KPI", "Tăng ca", "Thưởng sinh nhật");
    private static final List<String> deductionCategories = Arrays.asList("Phạt đi trễ", "Phạt quên điểm danh", "Thuế thu nhập cá nhân");

    @GetMapping("/payslip")
    public BaseResponse getPayslip(@RequestParam String companyId,
                                      @RequestParam String username,
                                      @RequestParam String yyyyMM) {
        BaseResponse<List<EmployeePayslipResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Tháng %s không phù hợp định dạng", yyyyMM));
                return response;
            }

            List<EmployeePayslipResponse> dataResponse = new ArrayList<>();

            String thisMonth = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMM");
            if (Integer.parseInt(yyyyMM) <= Integer.parseInt(thisMonth) ||
                    Integer.parseInt(yyyyMM) > 202002) {
                int n = new Random().nextInt(3) + 1;
                for (int i = 0; i < n; i++) {
                    EmployeePayslipResponse entity = fakeResponse(n, i, companyId, username, yyyyMM);
                    dataResponse.add(entity);
                }
            }

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getPayslip] [{}-{}-{}] ex", companyId, username, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private EmployeePayslipResponse fakeResponse(int n, int i, String companyId, String username, String yyyyMM) {
        EmployeePayslipResponse result = new EmployeePayslipResponse();

        // do buz
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate date = LocalDate.parse(yyyyMM + "01", formatter);
        int totalDaysInMonth = date.lengthOfMonth();
        LocalDate startDate = date.plusDays(i * totalDaysInMonth / n);
        LocalDate endDate = startDate.plusDays(totalDaysInMonth / n);
        if (i == n - 1) {
            endDate = date.plusDays(totalDaysInMonth - 1);
        }
        LocalDate payDate = endDate.plusDays(1);
        result.setDateFrom(startDate.format(formatter));
        result.setDateTo(endDate.format(formatter));
        result.setPayDate(payDate.format(formatter));
        result.setPaySlipId(String.format("#Payslip-%s-%s-%s_%s", companyId, username, yyyyMM, i));
        result.setIncomeList(new ArrayList<>());
        result.setDeductionList(new ArrayList<>());

        int incomeN = new Random().nextInt(5) + 1;
        int deductionN = new Random().nextInt(3) + 1;

        long totalIncome = 0L;
        for (int k = 0 ; k < incomeN; k++) {
            EmployeePayslipResponse.PayslipDetailValue detail = new EmployeePayslipResponse.PayslipDetailValue();
            detail.setCategory(incomeCategories.get(k));
            detail.setExtraInfo(new Random().nextInt(2) == 0 ? "" : "Đây là thông tin phụ");
            if (k == 0) {
                long amount = new Random().nextInt(10_000_000) + 5_000_000L;
                totalIncome += amount;
                detail.setAmount(String.format("%s VND", toVND(amount)));
            } else {
                long amount = new Random().nextInt(1_000_000) + 100_000L;
                totalIncome += amount;
                detail.setAmount(String.format("%s VND", toVND(amount)));
            }
            result.getIncomeList().add(detail);
        }

        long totalDeduction = 0L;
        for (int k = 0 ; k < deductionN; k++) {
            EmployeePayslipResponse.PayslipDetailValue detail = new EmployeePayslipResponse.PayslipDetailValue();
            detail.setCategory(deductionCategories.get(k));
            detail.setExtraInfo(new Random().nextInt(2) == 0 ? "" : "Đây là thông tin phụ");
            long amount = new Random().nextInt(100_000) + 500_000L;
            totalDeduction += amount;
            detail.setAmount(String.format("%s VND", toVND(amount)));
            result.getDeductionList().add(detail);
        }

        result.setTotalIncome(String.format("%s VND", toVND(totalIncome)));
        result.setTotalDeduction(String.format("%s VND", toVND(totalDeduction)));
        result.setTotalNetPay(String.format("%s VND", toVND(totalIncome - totalDeduction)));

        return result;
    }

    private static String toVND(long value){
        DecimalFormat        formatter = (DecimalFormat) NumberFormat.getInstance(Locale.US);
        DecimalFormatSymbols symbols   = formatter.getDecimalFormatSymbols();

        symbols.setGroupingSeparator('.');
        formatter.setDecimalFormatSymbols(symbols);
        return String.format("%s", formatter.format(value));
    }
}
