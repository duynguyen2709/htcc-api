package htcc.common.entity.order;

import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Request để đăng ký sử dụng hệ thống")
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = 130811L;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "HCMUS")
    private String  companyId = "";
    @ApiModelProperty(notes = "Email",
                      example = "admin@hcmus.edu.vn")
    private String  email     = "";
    @ApiModelProperty(notes = "Mã combo (nếu chọn gói combo)",
                      example = "CB1")
    private String  comboId   = "";
    @ApiModelProperty(notes = "Object tính năng, nếu chọn tính năng lẻ (chọn tính năng nào thì gửi featureId của tính năng đó, value = true), " +
            "\ntrừ EMPLOYEE_MANAGE là number (giống Combo)",
                      example = "{\"EMPLOYEE_MANAGE\":20,\"CHECKIN\":true,\"COMPLAINT\":true,\"CONTACT_LIST\":true," +
                              "\"DAY_OFF\":true,\"NOTIFICATION\":true,\"PERSONAL_INFO\":true,\"STATISTICS\":true," +
                              "\"WORKING_DAY\":true}")
    private Map<String, Object> requestedFeatures = new HashMap<>();

    @ApiModelProperty(notes = "* Bỏ qua")
    private boolean firstPay  = true;
    @ApiModelProperty(notes = "* Bỏ qua")
    private String orderId = "";

    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }

        return "";
    }
}
