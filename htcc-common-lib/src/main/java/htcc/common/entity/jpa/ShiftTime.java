package htcc.common.entity.jpa;

import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
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
    public int shiftId;

    @Column
    @ApiModelProperty(notes = "Giờ vào ca (HH:mm)",
                      example = "08:30")
    public String startTime = "";

    @Column
    @ApiModelProperty(notes = "Giờ kết thúc ca (HH:mm)",
                      example = "17:30")
    public String endTime = "";

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

        if (!DateTimeUtil.isRightFormat(startTime, "HH:mm")){
            return String.format("Giờ vào ca %s không phù hợp định dạng HH:mm", startTime);
        }

        if (!DateTimeUtil.isRightFormat(endTime, "HH:mm")){
            return String.format("Giờ ra ca %s không phù hợp định dạng HH:mm", endTime);
        }

        if (allowLateMinutes < 0){
            return "Số phút cho phép điểm danh trễ/ sớm không được nhỏ hơn 0";
        }

        return StringUtil.EMPTY;
    }

    public ShiftTime copy() {
        ShiftTime entity = new ShiftTime();
        entity.companyId = this.companyId;
        entity.officeId = this.officeId;
        entity.shiftId = this.shiftId;
        entity.startTime = this.startTime;
        entity.endTime = this.endTime;
        entity.allowLateMinutes = this.allowLateMinutes;
        return entity;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String companyId;
        public String officeId;
        public int shiftId;

        public Key(String key) {
            String[] val = key.split("_");

            this.companyId = val[0];
            this.officeId = val[1];
            this.shiftId = Integer.parseInt(val[2]);
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
            return (companyId.equalsIgnoreCase(that.companyId) && officeId.equalsIgnoreCase(that.officeId) &&
                    shiftId == that.shiftId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, officeId, shiftId);
        }
    }
}
