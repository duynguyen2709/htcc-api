package htcc.common.entity.jpa;

import htcc.common.constant.SessionEnum;
import htcc.common.constant.WeekDayEnum;
import htcc.common.constant.WorkingDayTypeEnum;
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
@ApiModel(description = "Thông tin ngày làm việc")
public class WorkingDay extends BaseJPAEntity {

    private static final long serialVersionUID = 592281858312515708L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @ApiModelProperty(notes = "ID định danh",
                      example = "1")
    public int id;

    @Column
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Column
    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    public String officeId = "";

    @Column
    @ApiModelProperty(notes = "Loại (1 : ngày thường/ 2 : ngày đặc biệt)",
                      example = "1")
    public int type = 1;

    @Column
    @ApiModelProperty(notes = "Thứ trong tuần",
                      example = "2")
    public int weekDay = 1;

    @Column
    @ApiModelProperty(notes = "Ngày nghỉ đặc biệt (nếu có) (yyyyMMdd)",
                      example = "20200424")
    public String date = "";

    @ApiModelProperty(notes = "Buổi nghỉ/làm việc (0 : cả ngày/ 1: buổi sáng / 2: buổi chiều / 3: buổi tối)",
                      example = "0")
    public int session = 0;

    @Column
    @ApiModelProperty(notes = "Ngày đó có làm việc hay không",
                      example = "true")
    public Boolean isWorking = true;

    @Column
    @ApiModelProperty(notes = "Thông tin thêm về ngày nghỉ/ ngày đi làm đặc biệt (vd: nghỉ lễ 30/4)",
                      example = "")
    public String extraInfo = "";

    public WorkingDay copy() {
        WorkingDay clone = new WorkingDay();
        clone.companyId = this.companyId;
        clone.officeId = this.officeId;
        clone.type = this.type;
        clone.weekDay = this.weekDay;
        clone.date = this.date;
        clone.session = this.session;
        clone.isWorking = this.isWorking;
        clone.extraInfo = this.extraInfo;
        return clone;
    }

    @Override
    public String isValid() {

        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(officeId)) {
            return "Mã chi nhánh không được rỗng";
        }

        if (WorkingDayTypeEnum.fromInt(type) == null){
            return String.format("Loại %s không hợp lệ", type);
        }

        if (SessionEnum.fromInt(session) == null) {
            return String.format("Buổi %s không hợp lệ", session);
        }

        if (type == WorkingDayTypeEnum.NORMAL.getValue()){
            if (WeekDayEnum.fromInt(weekDay) == null){
                return String.format("Thứ %s không hợp lệ", weekDay);
            }

            date = "";
            extraInfo = "";
        } else {
            if (!DateTimeUtil.isRightFormat(date, "yyyyMMdd")){
                return String.format("Ngày %s không phù hợp định dạng yyyyMMdd", date);
            }

            weekDay = 0;
        }


        return StringUtil.EMPTY;

    }
}
