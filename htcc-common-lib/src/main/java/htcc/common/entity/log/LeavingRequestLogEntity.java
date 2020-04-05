package htcc.common.entity.log;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class LeavingRequestLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "LeavingRequestLog";

    public String leavingRequestId = "";
    public String companyId        = "";
    public String username         = "";
    public long   clientTime       = 0L;
    public String category         = "";
    public String reason           = "";
    public String detail           = "";
    public String response         = "";
    public int    status           = 2;
    public String approver         = "";

    public LeavingRequestLogEntity(LeavingRequestModel model) {
        this.requestId = model.requestId;
        this.leavingRequestId = model.leavingRequestId;
        this.companyId = model.companyId;
        this.username = model.username;
        this.clientTime = model.clientTime;
        this.category = model.category;
        this.reason = model.reason;
        this.detail = StringUtil.toJsonString(model.detail);
        this.response = model.response;
        this.status = model.status;
        this.approver = model.approver;
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("leavingRequestId", this.leavingRequestId);
        params.put("companyId", this.companyId);
        params.put("username", this.username);
        params.put("clientTime", this.clientTime);
        params.put("category", this.category);
        params.put("reason", this.reason);
        params.put("detail", this.detail);
        params.put("response", this.response);
        params.put("status", this.status);
        params.put("approver", this.approver);
        return params;
    }

    @Override
    public long getCreateTime() {
        return this.clientTime;
    }

    @Override
    public String retrieveTableName() {
        return TABLE_NAME;
    }
}
