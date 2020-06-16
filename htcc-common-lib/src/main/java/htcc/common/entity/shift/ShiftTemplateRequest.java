package htcc.common.entity.shift;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ShiftTemplateRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 592257123408L;

    public String                    companyId     = "";
    public String                    templateName  = "";
    public String                    actor         = "";
    public List<ShiftTemplateDetail> shiftTimeList = new ArrayList<>();

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(templateName)) {
            return "Tên ca không được rỗng";
        }

        if (shiftTimeList == null || shiftTimeList.isEmpty()) {
            return "Danh sách ca không được rỗng";
        }

        for (ShiftTemplateDetail shift : shiftTimeList) {
            if (shift.weekDay < 1 || shift.weekDay > 7) {
                return String.format("Thứ %s không hợp lệ", shift.weekDay);
            }

            if (StringUtil.isEmpty(shift.officeId)) {
                return "Mã chi nhánh không được rỗng";
            }

            if (StringUtil.isEmpty(shift.shiftId)) {
                return "Mã ca không được rỗng";
            }
        }

        return StringUtil.EMPTY;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ShiftTemplateDetail implements Serializable {

        private static final long serialVersionUID = 5922818518L;

        public int    weekDay;
        public String officeId;
        public String shiftId;
    }
}
