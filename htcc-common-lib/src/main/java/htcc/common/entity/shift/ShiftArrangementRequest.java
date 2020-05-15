package htcc.common.entity.shift;

import htcc.common.constant.WeekDayEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShiftArrangementRequest extends BaseJPAEntity {

    public int    type        = 1;
    public String arrangeDate = "";
    public int    weekDay     = 0;
    public String companyId   = "";
    public String officeId    = "";
    public String username    = "";
    public String shiftId     = "";
    public String actor       = "";

    @Override
    public String isValid() {
        if (type != 1 && type != 2) {
            return "Loại ca không hợp lệ";
        }

        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(officeId)) {
            return "Mã chi nhánh không được rỗng";
        }

        if (StringUtil.isEmpty(shiftId)) {
            return "Mã ca không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên người dùng không được rỗng";
        }

        if (StringUtil.isEmpty(actor)) {
            return "Tên người xếp ca không được rỗng";
        }

        if (type == 2) {
            if (!DateTimeUtil.isRightFormat(arrangeDate, "yyyyMMdd")) {
                return String.format("Ngày xếp ca %s không phù hợp định dạng yyyyMMdd", arrangeDate);
            }
        }

        if (type == 1) {
            if (WeekDayEnum.fromInt(weekDay) == null || weekDay == 0) {
                return String.format("Thứ %s không hợp lệ", weekDay);
            }
        }

        return StringUtil.EMPTY;
    }
}
