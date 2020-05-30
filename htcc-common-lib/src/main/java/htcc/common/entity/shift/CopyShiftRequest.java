package htcc.common.entity.shift;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CopyShiftRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 5922123408L;

    public int    templateId = 0;
    public String companyId  = "";
    public String actor      = "";
    public String username   = "";

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
