package htcc.common.entity.complaint;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public String       content      = "";
    public List<String> images       = new ArrayList<>();
    public String       response     = "";
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
        this.content = request.content;
        this.complaintId = String.format("#%s-%s", this.companyId, this.requestId);
        this.response = StringUtil.EMPTY;
        this.status = ComplaintStatusEnum.PROCESSING.getValue();
        this.images = new ArrayList<>();
        // TODO : handle image upload
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
        this.content = logEntity.content;
        this.response = logEntity.response;
        this.status = logEntity.status;
        this.images = StringUtil.json2Collection(logEntity.images, StringUtil.LIST_STRING_TYPE);
    }
}
