package htcc.employee.service.controller.payslip;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.SalaryFormulaEnum;
import htcc.common.constant.SalaryFormulaTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.payslip.EmployeePayslipResponse;
import htcc.common.entity.payslip.SalaryFormula;
import htcc.common.entity.payslip.SalaryModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.salary.SalaryCalculationService;
import io.swagger.annotations.Api;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private SalaryCalculationService salaryCalculationService;

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

            List<SalaryModel> salaryModelList = salaryCalculationService.getPayslip(companyId, username, yyyyMM);
            for (SalaryModel salaryModel : salaryModelList) {
                EmployeePayslipResponse entity = calculateSalary(salaryModel, yyyyMM);
                dataResponse.add(entity);
            }

            response.setData(dataResponse);
        } catch (Exception e) {
            log.error("[getPayslip] [{}-{}-{}] ex", companyId, username, yyyyMM, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private EmployeePayslipResponse calculateSalary(SalaryModel model, String yyyyMM) {
        EmployeePayslipResponse result = new EmployeePayslipResponse();
        result.setPaySlipId("#" + model.getPaySlipId());
        result.setDateFrom(model.getLastPaymentDate());
        result.setDateTo(model.getNextPaymentDate());
        result.setPayDate(model.getLockDate());
        result.setTotalIncome(String.format("%s VND", toVND(model.getTotalIncome())));
        result.setTotalDeduction(String.format("%s VND", toVND(model.getTotalDeduction())));
        result.setTotalNetPay(String.format("%s VND", toVND(model.getTotalIncome() - model.getTotalDeduction())));
        result.setIncomeList(new ArrayList<>());
        result.setDeductionList(new ArrayList<>());

        Map<String, SalaryFormula.DetailFormula> formulaMap = new HashMap<>();
        formulaMap.put(SalaryFormulaEnum.BASE.getId(), model.getBaseSalary());
        formulaMap.put(SalaryFormulaEnum.EXTRA.getId(), model.getExtraSalary());
        formulaMap.put(SalaryFormulaEnum.MEAL.getId(), model.getMealMoney());
        formulaMap.put(SalaryFormulaEnum.OVERTIME.getId(), model.getOvertimeMoney());
        formulaMap.put(SalaryFormulaEnum.LATE.getId(), model.getLatePenalty());
        formulaMap.put(SalaryFormulaEnum.NON_PERMISSION_OFF.getId(), model.getNonPermissionOff());
        formulaMap.put(SalaryFormulaEnum.TAX.getId(), model.getTaxMoney());
        formulaMap.put(SalaryFormulaEnum.INSURANCE.getId(), model.getInsuranceMoney());
        formulaMap.put(SalaryFormulaEnum.PRE_TAX_REDUCTION.getId(), model.getPreTaxDependencyReduction());
        for (SalaryFormula.DetailFormula detail : model.getAdditionalIncome()) {
            formulaMap.put(detail.getFormulaId(), detail);
        }
        for (SalaryFormula.DetailFormula detail : model.getAdditionalPenalty()) {
            formulaMap.put(detail.getFormulaId(), detail);
        }

        // Base Salary
        EmployeePayslipResponse.PayslipDetailValue baseSalary = new EmployeePayslipResponse.PayslipDetailValue();
        baseSalary.setCategory(model.getBaseSalary().getDescription());
        baseSalary.setExtraInfo(StringUtil.EMPTY);
        baseSalary.setAmount(String.format("%s VND", toVND(model.getBaseSalary().getTotalAmount())));
        if (model.getBaseSalary().getType() == 2) {
            baseSalary.setExtraInfo(String.format("= %s đ/h * số giờ", model.getBaseSalary().getValue()));
        }
        result.getIncomeList().add(baseSalary);


        // Extra Salary
        if (model.getExtraSalary().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue extraSalary = new EmployeePayslipResponse.PayslipDetailValue();
            extraSalary.setCategory(model.getExtraSalary().getDescription());
            extraSalary.setExtraInfo(StringUtil.EMPTY);
            extraSalary.setAmount(String.format("%s VND", toVND(model.getExtraSalary().getTotalAmount())));
            result.getIncomeList().add(extraSalary);
        }


        // Meal Money
        if (model.getMealMoney().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue mealMoney = new EmployeePayslipResponse.PayslipDetailValue();
            mealMoney.setCategory(model.getMealMoney().getDescription());
            mealMoney.setExtraInfo(StringUtil.EMPTY);
            mealMoney.setAmount(String.format("%s VND", toVND(model.getMealMoney().getTotalAmount())));
            result.getIncomeList().add(mealMoney);
        }


        // Overtime Money
        if (model.getOvertimeMoney().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue overtimeMoney = new EmployeePayslipResponse.PayslipDetailValue();
            overtimeMoney.setCategory(model.getOvertimeMoney().getDescription());
            overtimeMoney.setExtraInfo(String.format("= %s đ/h * số giờ", model.getOvertimeMoney().getValue()));
            overtimeMoney.setAmount(String.format("%s VND", toVND(model.getOvertimeMoney().getTotalAmount())));
            result.getIncomeList().add(overtimeMoney);
        }

        for (SalaryFormula.DetailFormula formula : model.getAdditionalIncome()) {
            if (formula.getValue() > 0) {
                EmployeePayslipResponse.PayslipDetailValue detail = new EmployeePayslipResponse.PayslipDetailValue();
                detail.setCategory(formula.getDescription());
                detail.setAmount(String.format("%s VND", toVND(formula.getTotalAmount())));
                if (formula.getType() == SalaryFormulaTypeEnum.PERCENTAGE.getValue()) {
                    SalaryFormula.DetailFormula baseOn = formulaMap.get(formula.getIdBasedOn());
                    String desc = (baseOn == null) ? "" : baseOn.getDescription();
                    String extra = String.format("= %s x %s%%", desc, formula.getValue());
                    detail.setExtraInfo(extra);
                }
                result.getIncomeList().add(detail);
            }
        }

        // Deduction List

        // Late Penalty
        if (model.getLatePenalty().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue latePenalty = new EmployeePayslipResponse.PayslipDetailValue();
            latePenalty.setCategory(model.getLatePenalty().getDescription());
            latePenalty.setExtraInfo(String.format("= %s đ * số lần", model.getLatePenalty().getValue()));
            latePenalty.setAmount(String.format("%s VND", toVND(model.getLatePenalty().getTotalAmount())));
            result.getDeductionList().add(latePenalty);
        }

        // Non Permission Off
        if (model.getNonPermissionOff().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue nonPermissionOff = new EmployeePayslipResponse.PayslipDetailValue();
            nonPermissionOff.setCategory(model.getLatePenalty().getDescription());
            nonPermissionOff.setExtraInfo(String.format("= %s đ * số ngày", model.getLatePenalty().getValue()));
            nonPermissionOff.setAmount(String.format("%s VND", toVND(model.getLatePenalty().getTotalAmount())));
            result.getDeductionList().add(nonPermissionOff);
        }

        // Insurance
        if (model.getInsuranceMoney().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue insurance = new EmployeePayslipResponse.PayslipDetailValue();
            insurance.setCategory(model.getInsuranceMoney().getDescription());
            insurance.setExtraInfo(String.format("= Lương cơ bản x %s%%", model.getInsuranceMoney().getValue()));
            insurance.setAmount(String.format("%s VND", toVND(model.getInsuranceMoney().getTotalAmount())));
            result.getDeductionList().add(insurance);
        }

        // Tax
        if (model.getTaxMoney().getValue() > 0) {
            EmployeePayslipResponse.PayslipDetailValue tax = new EmployeePayslipResponse.PayslipDetailValue();
            tax.setCategory(model.getTaxMoney().getDescription());
            String preTax = model.getPreTaxDependencyReduction().getValue() > 0 ?
                    String.format(" - Giảm trừ phụ thuộc %s đ", (long)model.getPreTaxDependencyReduction().getValue()) : "";
            tax.setExtraInfo(String.format("= (Tổng thu nhập %s- Các khoản không tính thuế) x %s%%",
                    preTax, model.getTaxMoney().getValue()));
            tax.setAmount(String.format("%s VND", toVND(model.getTaxMoney().getTotalAmount())));
            result.getDeductionList().add(tax);
        }

        // Additional Deduction
        for (SalaryFormula.DetailFormula formula : model.getAdditionalPenalty()) {
            if (formula.getValue() > 0) {
                EmployeePayslipResponse.PayslipDetailValue detail = new EmployeePayslipResponse.PayslipDetailValue();
                detail.setCategory(formula.getDescription());
                detail.setAmount(String.format("%s VND", toVND(formula.getTotalAmount())));
                if (formula.getType() == SalaryFormulaTypeEnum.PERCENTAGE.getValue()) {
                    SalaryFormula.DetailFormula baseOn = formulaMap.get(formula.getIdBasedOn());
                    String desc = (baseOn == null) ? "" : baseOn.getDescription();
                    String extra = String.format("= %s x %s%%", desc, formula.getValue());
                    detail.setExtraInfo(extra);
                }
                result.getDeductionList().add(detail);
            }
        }

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
