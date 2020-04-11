package htcc.common.entity.complaint;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.util.StringUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Data
public class ComplaintModel implements Serializable {

    private static final long serialVersionUID = 5913715583005150708L;

    public String       requestId    = LoggingConfiguration.getTraceId();
    public String       complaintId = "";
    public int          receiverType = 2;
    public int          isAnonymous  = 0;
    public String       companyId    = "";
    public String       username     = "";
    public long         clientTime   = 0L;
    public String       category     = "";
    public List<String> content      = new ArrayList<>();
    public List<String> images       = new ArrayList<>();
    public List<String> response       = new ArrayList<>();
    public int          status       = 2;

    public ComplaintModel(ComplaintRequest request) {
        this.requestId = LoggingConfiguration.getTraceId();
        this.receiverType = request.receiverType;
        this.isAnonymous = request.isAnonymous;
        this.companyId = request.companyId;
        this.username = request.username;
        if (this.isAnonymous == 1) {
            if (this.receiverType == 1) {
                // send anonymous to admin system
                this.companyId = StringUtil.EMPTY;
            }
            this.username = StringUtil.EMPTY;
        }
        this.clientTime = request.clientTime;
        this.category = request.category;
        this.content = Collections.singletonList(request.content);
        this.complaintId = String.format("#%s-%s", this.companyId, this.requestId);
        this.response = new ArrayList<>();
        this.status = ComplaintStatusEnum.PROCESSING.getValue();

        // upload image async and then update this.images value
        this.images = new ArrayList<>();
    }

    public ComplaintModel(ComplaintLogEntity logEntity) {
        this.requestId = logEntity.requestId;
        this.complaintId = logEntity.complaintId;
        this.receiverType = logEntity.receiverType;
        this.isAnonymous = logEntity.isAnonymous;
        this.companyId = logEntity.companyId;
        this.username = logEntity.username;
        this.clientTime = logEntity.clientTime;
        this.category = logEntity.category;
        this.status = logEntity.status;
        this.content = StringUtil.json2Collection(logEntity.content, StringUtil.LIST_STRING_TYPE);
        this.response = StringUtil.json2Collection(logEntity.response, StringUtil.LIST_STRING_TYPE);
        this.images = StringUtil.json2Collection(logEntity.images, StringUtil.LIST_STRING_TYPE);
    }
}
