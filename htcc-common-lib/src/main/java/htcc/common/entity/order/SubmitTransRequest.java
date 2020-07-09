package htcc.common.entity.order;

import htcc.common.constant.PaymentCycleTypeEnum;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitTransRequest implements Serializable {

    private static final long serialVersionUID = 130812L;

    private String  orderId = "";
    private String  paymentName   = "";
    private String  paymentId = "";
    private int     paymentCycleType = PaymentCycleTypeEnum.MONTHLY.getValue();

    public String isValid() {
        if (StringUtil.isEmpty(paymentName)) {
            return "Tên người thanh toán không hợp lệ";
        }
        if (StringUtil.isEmpty(paymentId)) {
            return "Mã thanh toán không hợp lệ";
        }
        if (StringUtil.isEmpty(orderId)) {
            return "Mã đơn hàng không hợp lệ";
        }
        if (PaymentCycleTypeEnum.fromInt(paymentCycleType) == null) {
            return "Loại thanh toán không hợp lệ";
        }
        return "";
    }
}
