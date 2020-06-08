package htcc.common.entity.shift;

import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ShiftArrangementModel implements Serializable {

    private static final long serialVersionUID = 1926325150807L;

    public String  arrangementId    = "";
    public long    actionTime       = System.currentTimeMillis();
    public int     week             = 0;
    public String  arrangeDate      = "";
    public String  companyId        = "";
    public String  officeId         = "";
    public String  username         = "";
    public ShiftTime shiftTime = new ShiftTime();
//    public String  shiftId          = "";
//    public String  startTime        = "";
//    public String  endTime          = "";
//    public float   dayCount         = 0;
//    public boolean allowDiffTime    = false;
//    public int     allowLateMinutes = 0;
    public String  actor            = "";
    public boolean isFixed          = false;

    public ShiftArrangementModel(ShiftArrangementRequest request) {
        this.actionTime = System.currentTimeMillis();
        this.arrangeDate = request.getArrangeDate();
        this.week = DateTimeUtil.getWeekNum(request.getArrangeDate());
        this.companyId = request.getCompanyId();
        this.officeId = request.getOfficeId();
        this.username = request.getUsername();
        this.shiftTime.shiftId = request.getShiftId();
        this.actor = request.getActor();
        this.arrangementId = String.format("%s-%s-%s-%s-%s-%s",
                this.arrangeDate, this.companyId, this.officeId, request.getShiftId(),
                        this.username, request.getType());
        this.isFixed = false;
    }

    public void setShiftData(ShiftTime shiftTime) {
        this.shiftTime = shiftTime;
    }

    public ShiftArrangementModel(ShiftArrangementLogEntity model) {
        this.arrangementId = model.getArrangementId();
        this.actionTime = model.getActionTime();
        this.week = model.getWeek();
        this.arrangeDate = model.getArrangeDate();
        this.companyId = model.getCompanyId();
        this.officeId = model.getOfficeId();
        this.username = model.getUsername();
        this.shiftTime = StringUtil.fromJsonString(model.getShiftTime(), ShiftTime.class);
        this.actor = model.getActor();
        this.isFixed = (model.getIsFixed() == 1);
    }
}
