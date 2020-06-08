package htcc.common.entity.shift;

import htcc.common.component.LoggingConfiguration;
import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ShiftArrangementLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "ShiftArrangementLog";

    public String arrangementId    = "";
    public long   actionTime       = 0L;
    public int    week             = 0;
    public String arrangeDate      = "";
    public String companyId        = "";
    public String officeId         = "";
    public String username         = "";
    public String shiftTime          = "";
    public String actor            = "";
    public int    isFixed          = 0;

    public ShiftArrangementLogEntity(ShiftArrangementModel model) {
        this.requestId = LoggingConfiguration.getTraceId();
        this.arrangementId = model.getArrangementId();
        this.actionTime = model.getActionTime();
        this.week = model.getWeek();
        this.arrangeDate = model.getArrangeDate();
        this.companyId = model.getCompanyId();
        this.officeId = model.getOfficeId();
        this.username = model.getUsername();
        this.shiftTime = StringUtil.toJsonString(model.getShiftTime());
        this.actor = model.getActor();
        this.isFixed = (model.isFixed() ? 1 : 0);
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("arrangementId", this.arrangementId);
        params.put("actionTime", this.actionTime);
        params.put("week", this.week);
        params.put("arrangeDate", this.arrangeDate);
        params.put("companyId", this.companyId);
        params.put("officeId", this.officeId);
        params.put("username", this.username);
        params.put("shiftTime", this.shiftTime);
        params.put("actor", this.actor);
        params.put("isFixed", this.isFixed);
        return params;
    }

    @Override
    public long getCreateTime() {
        return DateTimeUtil.parseStringToDate(this.arrangeDate, "yyyyMMdd").getTime();
    }

    @Override
    public String retrieveTableName() {
        return TABLE_NAME;
    }
}
