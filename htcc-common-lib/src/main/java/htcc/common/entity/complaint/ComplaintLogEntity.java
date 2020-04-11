package htcc.common.entity.complaint;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.entity.complaint.ComplaintModel;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class ComplaintLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "ComplaintLog";

    public String complaintId  = "";
    public String companyId    = "";
    public String username     = "";
    public long   clientTime   = 0L;
    public int    receiverType = 2;
    public int    isAnonymous  = 0;
    public String category     = "";
    public String content      = "";
    public String images       = "";
    public String response     = "";
    public int    status       = 2;

    public ComplaintLogEntity(ComplaintModel model) {
        this.requestId = model.requestId;
        this.companyId = model.companyId;
        this.username = model.username;
        this.clientTime = model.clientTime;
        this.receiverType = model.receiverType;
        this.isAnonymous = model.isAnonymous;
        this.category = model.category;
        this.content = StringUtil.toJsonString(model.content);
        this.images = StringUtil.toJsonString(model.images);
        this.response = StringUtil.toJsonString(model.response);
        this.status = model.status;
        this.complaintId = model.complaintId;
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("complaintId", this.complaintId);
        params.put("companyId", this.companyId);
        params.put("username", this.username);
        params.put("clientTime", this.clientTime);
        params.put("receiverType", this.receiverType);
        params.put("isAnonymous", this.isAnonymous);
        params.put("category", this.category);
        params.put("content", this.content);
        params.put("images", this.images);
        params.put("response", this.response);
        params.put("status", this.status);
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
