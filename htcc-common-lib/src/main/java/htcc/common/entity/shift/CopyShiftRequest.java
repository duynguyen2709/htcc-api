package htcc.common.entity.shift;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class CopyShiftRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 5922123408L;

    public String companyId  = "";
    public String actor      = "";
    public String username   = "";

    public Map<Integer, List<MiniShiftTime>> data = new HashMap<>();

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(actor)) {
            return "Tên người xếp ca không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên nhân viên không được rỗng";
        }

        return StringUtil.EMPTY;
    }
}
