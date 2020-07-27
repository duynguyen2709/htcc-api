package htcc.common.entity.payslip;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeePayslipResponse {

    private static final long serialVersionUID = 2713L;

    // mã phiếu lương
    private String paySlipId = "";
//    // loại trả lương (theo tháng, theo tuần, theo ngày)
    private String paymentType = "";
    // ngày phát lương
    private String payDate = "";
    // ngày bắt đầu tính lương
    private String dateFrom = "";
    // ngày kết thúc tính lương
    private String dateTo = "";
    // tổng thu nhập
    private String totalIncome = "";
    // tổng khấu trừ
    private String totalDeduction = "";
    // tổng thực lãnh
    private String totalNetPay = "";
    // danh sách các khoản thu nhập
    private List<PayslipDetailValue> incomeList = new ArrayList<>();
    // danh sách các khoản khấu trừ
    private List<PayslipDetailValue> deductionList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class PayslipDetailValue {
        // loại thu nhập/ khấu trừ (thuế, lương cứng...)
        private String category = "";
        // thông tin thêm (VD: phạt trừ vì lý do gì)
        private String extraInfo = "";
        // giá trị
        private String amount = "";
    }
}
