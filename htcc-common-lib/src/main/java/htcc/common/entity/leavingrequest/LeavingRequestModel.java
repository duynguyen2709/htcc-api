package htcc.common.entity.leavingrequest;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class LeavingRequestModel implements Serializable {

    private static final long serialVersionUID = 5913716683005150708L;

    public String  requestId        = "";
    public String  leavingRequestId = "";
    public String  companyId        = "";
    public String  username         = "";
    public long    clientTime       = 0L;
    public boolean useDayOff        = true;
    public boolean hasSalary        = false;
    public String  category         = "";
    public String  reason           = "";
    public String  response         = "";
    public int     status           = 2;
    public String  approver         = "";

    public List<LeavingRequest.LeavingDayDetail> detail = new ArrayList<>();

    public LeavingRequestModel(LeavingRequest request) {
        this.requestId = LoggingConfiguration.getTraceId();
        this.companyId = request.companyId;
        this.username = request.username;
        this.clientTime = request.clientTime;
        this.category = request.category;
        this.reason = request.reason;
        this.leavingRequestId = String.format("#%s-LR-%s", this.companyId, this.requestId);
        this.response = StringUtil.EMPTY;
        this.status = ComplaintStatusEnum.PROCESSING.getValue();
        this.detail = request.detail;
        this.approver = StringUtil.EMPTY;
        this.detail.sort(LeavingRequest.LeavingDayDetail.getComparator());
    }

    public LeavingRequestModel(LeavingRequestLogEntity logEntity) {
        this.requestId = logEntity.requestId;
        this.leavingRequestId = logEntity.leavingRequestId;
        this.companyId = logEntity.companyId;
        this.username = logEntity.username;
        this.clientTime = logEntity.clientTime;
        this.useDayOff = (logEntity.useDayOff == 1);
        this.hasSalary = (logEntity.hasSalary == 1);
        this.category = logEntity.category;
        this.reason = logEntity.reason;
        this.response = logEntity.response;
        this.status = logEntity.status;
        this.approver = logEntity.approver;
        this.detail =StringUtil.json2Collection(logEntity.detail,
                new TypeToken<List<LeavingRequest.LeavingDayDetail>>(){}.getType());
    }
}
