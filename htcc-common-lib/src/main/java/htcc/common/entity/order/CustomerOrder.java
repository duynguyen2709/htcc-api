package htcc.common.entity.order;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerOrder extends BaseJPAEntity {

    private static final long serialVersionUID = 137L;

    @Id
    private String companyId = "";
    @Column
    private String email = "";
    @Column
    private String lastPaymentDate = "";
    @Column
    private String nextPaymentDate = "";
    @Column
    private long totalPrice = 0L;
    @Column
    private String comboDetail = "";
    @Column
    private String supportedFeatures = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }

        if (!StringUtil.isEmail(email)) {
            return "Email không hợp lệ";
        }

        if (!DateTimeUtil.isRightFormat(nextPaymentDate, "yyyyMMdd")) {
            return "Ngày thanh toán không hợp lệ";
        }

        if (totalPrice <= 0) {
            return "Tổng tiền không hợp lệ";
        }

        if (StringUtil.isEmpty(supportedFeatures)) {
            return "Gói chức năng không hợp lệ";
        }
        return StringUtil.EMPTY;
    }

    public CustomerOrder(DetailOrderModel model) {
        this.companyId = model.getCompanyId();
        this.email = model.getEmail();
        this.lastPaymentDate = model.getLastPaymentDate();
        this.nextPaymentDate = model.getNextPaymentDate();
        this.totalPrice = model.getTotalPrice();
        this.comboDetail = StringUtil.toJsonString(model.getComboDetail());
        this.supportedFeatures = StringUtil.toJsonString(model.getSupportedFeatures());
    }
}
