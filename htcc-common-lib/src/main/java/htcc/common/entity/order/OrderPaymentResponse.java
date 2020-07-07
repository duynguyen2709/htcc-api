package htcc.common.entity.order;

import htcc.common.constant.FeatureEnum;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.PaymentCycleTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPaymentResponse implements Serializable {

    private static final long serialVersionUID = 130814L;

    private String orderId = "";
    private String date = "";

    private String companyId = "";
    private String email     = "";

    private String  paymentName = "";
    private String  paymentId = "";
    private int     paymentCycleType = PaymentCycleTypeEnum.MONTHLY.getValue();

    private String comboName = "";
    private List<String> featureList = new ArrayList<>();

    private long totalPrice = 0L;
    private long paidPrice = 0L;

    private int orderStatus = OrderStatusEnum.CREATED.getValue();

    public OrderPaymentResponse(DetailOrderModel model) {
        this.orderId = model.getOrderId();
        this.date = model.getDate();
        this.companyId = model.getCompanyId();
        this.email = model.getEmail();
        this.paymentName = model.getPaymentName();
        this.paymentId = model.getPaymentId();
        this.paymentCycleType = model.getPaymentCycleType();
        if (model.getComboDetail() != null && model.getComboDetail().getComboName() != null) {
            this.comboName = model.getComboDetail().getComboName();
        }
        this.orderStatus = model.getOrderStatus();
        this.totalPrice = model.getTotalPrice();
        this.paidPrice = (paymentCycleType == PaymentCycleTypeEnum.MONTHLY.getValue() ?
                model.getTotalPrice() / 12 :
                model.getTotalPrice());

        for (DetailOrderModel.SupportedFeature entity : model.getSupportedFeatures()) {
            String featureName = "";
            if (entity.getFeature().getFeatureId().equals(FeatureEnum.EMPLOYEE_MANAGE.getValue())) {
                int numEmployees = (int)(Float.parseFloat(String.valueOf(entity.getValue())));
                featureName = String.format("%s : %s nhân viên", entity.getFeature().getFeatureName(), numEmployees);
            }
            else {
                featureName = entity.getFeature().getFeatureName();
            }
            this.featureList.add(featureName);
        }
    }
}
