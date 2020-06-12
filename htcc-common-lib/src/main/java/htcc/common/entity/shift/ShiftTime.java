package htcc.common.entity.shift;

import htcc.common.constant.SessionEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@IdClass(ShiftTime.Key.class)
@NoArgsConstructor
@AllArgsConstructor
public class ShiftTime extends BaseJPAEntity {

    private static final long serialVersionUID = 16368583005199807L;

    @Id
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Id
    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    public String officeId = "";

    @Id
    @ApiModelProperty(notes = "Mã ca",
                      example = "1")
    public String shiftId;

    @Column
    @ApiModelProperty(notes = "Tên ca",
                      example = "abc")
    public String shiftName = "";

    @Column
    @ApiModelProperty(notes = "Giờ vào ca (HH:mm)",
                      example = "08:30")
    public String startTime = "";

    @Column
    @ApiModelProperty(notes = "Giờ kết thúc ca (HH:mm)",
                      example = "17:30")
    public String endTime = "";

    @Column
    public int session = SessionEnum.FULL_DAY.getValue();

    @Column
    @ApiModelProperty(notes = "Số ngày công để chấm công",
                      example = "1")
    public float dayCount = 0;

    @Column
    @ApiModelProperty(notes = "Cho phép điểm danh đủ ca không cần đúng giờ",
                      example = "false")
    public boolean allowDiffTime = false;

    @Column
    @ApiModelProperty(notes = "Số phút cho phép điểm danh trễ/ sớm",
                      example = "5")
    public int allowLateMinutes = 0;

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

        if (StringUtil.isEmpty(shiftName)) {
            return "Tên ca không được rỗng";
        }

        if (!DateTimeUtil.isRightFormat(startTime, "HH:mm")){
            return String.format("Giờ vào ca %s không phù hợp định dạng HH:mm", startTime);
        }

        if (!DateTimeUtil.isRightFormat(endTime, "HH:mm")){
            return String.format("Giờ ra ca %s không phù hợp định dạng HH:mm", endTime);
        }

        if (allowLateMinutes < 0){
            return "Số phút cho phép điểm danh trễ/ sớm không được nhỏ hơn 0";
        }

        if (dayCount <= 0) {
            return "Số ngày công phải lớn hơn 0";
        }

        return StringUtil.EMPTY;
    }

    public ShiftTime copy() {
        ShiftTime entity = new ShiftTime();
        entity.companyId = this.companyId;
        entity.officeId = this.officeId;
        entity.shiftId = this.shiftId;
        entity.shiftName = this.shiftName;
        entity.startTime = this.startTime;
        entity.endTime = this.endTime;
        entity.session = this.session;
        entity.dayCount = this.dayCount;
        entity.allowDiffTime = this.allowDiffTime;
        entity.allowLateMinutes = this.allowLateMinutes;
        return entity;
    }

    public void calculateSession() {
        if (dayCount == 1) {
            session = SessionEnum.FULL_DAY.getValue();
        } else if (dayCount == 0.5f) {
            if (DateTimeUtil.isBeforeMidDay(startTime)) {
                session = SessionEnum.MORNING.getValue();
            } else {
                session = SessionEnum.AFTERNOON.getValue();
            }
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String companyId;
        public String officeId;
        public String shiftId;

        public Key(String key) {
            String[] val = key.split("_");

            this.companyId = val[0];
            this.officeId = val[1];
            this.shiftId = val[2];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ShiftTime.Key that = (ShiftTime.Key) o;
            return (companyId.equalsIgnoreCase(that.companyId) &&
                    officeId.equalsIgnoreCase(that.officeId) &&
                    shiftId.equalsIgnoreCase(that.shiftId));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, officeId, shiftId);
        }
    }
}
