package htcc.common.entity.order;

import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateOrderRequest implements Serializable {

    private static final long serialVersionUID = 130811L;

    private String  companyId = "";
    private String  email     = "";
    private String  comboId   = "";
    private boolean firstPay  = true;

    private Map<String, Object> requestedFeatures = new HashMap<>();

    private String orderId = "";

    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }

        return "";
    }
}
