package htcc.common.entity.order;

import htcc.common.constant.OrderStatusEnum;
import htcc.common.constant.PaymentCycleTypeEnum;
import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "OrderLog";

    public String orderId            = "";
    public String date               = "";
    public String companyId          = "";
    public String email              = "";
    public String lastPaymentDate    = "";
    public String nextPaymentDate    = "";
    public String paymentName        = "";
    public String paymentId          = "";
    public int    paymentCycleType   = PaymentCycleTypeEnum.MONTHLY.getValue();
    public long   paymentTime        = System.currentTimeMillis();
    public String comboDetail        = "";
    public String supportedFeatures  = "";
    public long   totalPrice         = 0L;
    public float  discountPercentage = 0.0f;
    public int    orderStatus        = OrderStatusEnum.CREATED.getValue();
    public int    firstPay           = 1;

    public OrderLogEntity(DetailOrderModel model) {
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
        this.comboDetail = StringUtil.toJsonString(model.getComboDetail());
        this.supportedFeatures = StringUtil.toJsonString(model.getSupportedFeatures());
        this.totalPrice = model.getTotalPrice();
        this.discountPercentage = model.getDiscountPercentage();
        this.orderStatus = model.getOrderStatus();
        this.firstPay = model.isFirstPay() ? 1 : 0;
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("requestId", this.requestId);
        map.put("orderId", this.orderId);
        map.put("date", this.date);
        map.put("companyId", this.companyId);
        map.put("email", this.email);
        map.put("lastPaymentDate", this.lastPaymentDate);
        map.put("nextPaymentDate", this.nextPaymentDate);
        map.put("paymentName", this.paymentName);
        map.put("paymentId", this.paymentId);
        map.put("paymentCycleType", this.paymentCycleType);
        map.put("paymentTime", this.paymentTime);
        map.put("comboDetail", this.comboDetail);
        map.put("supportedFeatures", this.supportedFeatures);
        map.put("totalPrice", this.totalPrice);
        map.put("discountPercentage", this.discountPercentage);
        map.put("orderStatus", this.orderStatus);
        map.put("firstPay", this.firstPay);
        return map;
    }

    @Override
    public long getCreateTime() {
        return this.paymentTime;
    }

    @Override
    public String retrieveTableName() {
        return TABLE_NAME;
    }
}
