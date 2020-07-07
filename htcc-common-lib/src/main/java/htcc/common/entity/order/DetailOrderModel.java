package htcc.common.entity.order;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.PaymentCycleTypeEnum;
import htcc.common.entity.feature.FeatureCombo;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderModel implements Serializable {

    private static final long serialVersionUID = 130808L;

    private String orderId = "";
    private String date = "";

    private String companyId = "";
    private String email     = "";

    private String lastPaymentDate = "";
    private String nextPaymentDate = "";

    // payment - submittrans
    private String  paymentName   = "";
    private String  paymentId = "";
    private int     paymentCycleType = PaymentCycleTypeEnum.MONTHLY.getValue();
    private long    paymentTime = System.currentTimeMillis();

    private FeatureCombo comboDetail = null;
    private List<SupportedFeature> supportedFeatures = new ArrayList<>();

    private long totalPrice = 0L;
    private float discountPercentage = 0.0f;

    private int orderStatus = OrderStatusEnum.CREATED.getValue();
    private boolean firstPay = true;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SupportedFeature implements Serializable {
        private FeaturePrice feature;
        private Object       value;
    }

    public DetailOrderModel(CustomerOrder order) {
        this.companyId = order.getCompanyId();
        this.email = order.getEmail();
        this.totalPrice = order.getTotalPrice();
        this.lastPaymentDate = order.getLastPaymentDate();
        this.nextPaymentDate = order.getNextPaymentDate();
        this.supportedFeatures = StringUtil.json2Collection(order.getSupportedFeatures(),
                new TypeToken<List<SupportedFeature>>() {}.getType());
        if (!StringUtil.isEmpty(order.getComboDetail())) {
            this.comboDetail = StringUtil.fromJsonString(order.getComboDetail(), FeatureCombo.class);
            this.discountPercentage = this.comboDetail.getDiscountPercentage();
        }
        if (!StringUtil.isEmpty(order.getLastPaymentDate())) {
            this.firstPay = false;
        }
    }

    public DetailOrderModel(OrderLogEntity model) {
        this.orderId = model.getOrderId();
        this.date = model.getDate();
        this.companyId = model.getCompanyId();
        this.email = model.getEmail();
        this.lastPaymentDate = model.getLastPaymentDate();
        this.nextPaymentDate = model.getNextPaymentDate();
        this.paymentName = model.getPaymentName();
        this.paymentId = model.getPaymentId();
        this.paymentCycleType = model.getPaymentCycleType();
        this.paymentTime = model.getPaymentTime();
        this.comboDetail = StringUtil.fromJsonString(model.getComboDetail(), FeatureCombo.class);
        this.supportedFeatures = StringUtil.json2Collection(model.getSupportedFeatures(),
                new TypeToken<List<SupportedFeature>>() {}.getType());
        this.totalPrice = model.getTotalPrice();
        this.discountPercentage = model.getDiscountPercentage();
        this.orderStatus = model.getOrderStatus();
        this.firstPay = model.getFirstPay() == 1;
    }
}
