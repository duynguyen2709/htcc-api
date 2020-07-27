package htcc.common.entity.payslip;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
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
public class ManagerLockSalaryRequest extends BaseJPAEntity {
    private static final long serialVersionUID = 2713L;

    private String actor = "";
    private String yyyyMM = "";
    private List<String> paySlipIdList = new ArrayList<>();

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(actor)) {
            return "Tên người thực hiện không hợp lệ";
        }
        if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
            return "Tháng không hợp lệ";
        }
        if (paySlipIdList == null || paySlipIdList.isEmpty()) {
            return "Danh sách bảng lương cần khóa không được rỗng";
        }
        return StringUtil.EMPTY;
    }
}
