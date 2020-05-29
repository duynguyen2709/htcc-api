package htcc.common.entity.shift;

import htcc.common.constant.SessionEnum;
import htcc.common.constant.WeekDayEnum;
import htcc.common.constant.WorkingDayTypeEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class FixedShiftArrangement extends BaseJPAEntity {

    private static final long serialVersionUID = 5922818515708L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID định danh",
                      example = "1")
    public int id;

    @Column
    public String companyId = "";

    @Column
    public String officeId = "";

    @Column
    public String username = "";

    @Column
    public String shiftId = "";

    @Column
    @ApiModelProperty(notes = "Thứ trong tuần",
                      example = "2")
    public int weekDay = 1;

    @Column
    public String actor = "";

    public FixedShiftArrangement(ShiftArrangementRequest request) {
        this.companyId = request.getCompanyId();
        this.officeId = request.getOfficeId();
        this.username = request.getUsername();
        this.shiftId = request.getShiftId();
        this.weekDay = request.getWeekDay();
        this.actor = request.getActor();
    }

    @Override
    public String isValid() {

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

        if (WeekDayEnum.fromInt(weekDay) == null){
            return String.format("Thứ %s không hợp lệ", weekDay);
        }

        if (StringUtil.isEmpty(actor)) {
            return "Tên người xếp ca không được rỗng";
        }

        return StringUtil.EMPTY;
    }
}
