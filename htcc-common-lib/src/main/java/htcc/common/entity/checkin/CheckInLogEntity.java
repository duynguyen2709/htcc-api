package htcc.common.entity.checkin;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
public class CheckInLogEntity extends BaseLogEntity {

    private static final String TABLE_NAME = "CheckInLog";

    public String  checkInId        = "";
    public int     subType          = 1;
    public String  companyId        = "";
    public String  officeId         = "";
    public String  username         = "";
    public long    clientTime       = 0L;
    public long    serverTime       = 0L;
    public String  validTime        = "00:00";
    public boolean isOnTime         = true;
    public float   validLatitude    = 0.0f;
    public float   validLongitude   = 0.0f;
    public float   latitude         = 0.0f;
    public float   longitude        = 0.0f;
    public int     maxAllowDistance = 0;
    public boolean usedWifi         = false;
    public String  ip               = "";
    public String  image            = "";
    public String  reason           = "";
    public String  approver         = "";
    public int     status           = 1;

    public CheckInLogEntity(CheckinModel model) {
        this.requestId = model.getRequestId();
        this.checkInId = model.checkInId;
        this.subType = model.subType;
        this.companyId = model.companyId;
        this.officeId = model.officeId;
        this.username = model.username;
        this.clientTime = model.clientTime;
        this.serverTime = model.serverTime;
        this.validTime = model.validTime;
        this.isOnTime = model.isOnTime;
        this.validLatitude = model.validLatitude;
        this.validLongitude = model.validLongitude;
        this.latitude = model.latitude;
        this.longitude = model.longitude;
        this.maxAllowDistance = model.maxAllowDistance;
        this.usedWifi = model.usedWifi;
        this.ip = model.ip;
        this.image = model.image;
        this.reason = model.reason;
        this.status = model.status;
    }

    @Override
    public Map<String, Object> getParamsMap() {
        Map<String, Object> params = new HashMap<>();
        params.put("requestId", this.requestId);
        params.put("checkInId", this.checkInId);
        params.put("subType", this.subType);
        params.put("companyId", this.companyId);
        params.put("officeId", this.officeId);
        params.put("username", this.username);
        params.put("clientTime", this.clientTime);
        params.put("serverTime", this.serverTime);
        params.put("validTime", this.validTime);
        params.put("isOnTime", this.isOnTime ? 1 : 0);
        params.put("validLatitude", this.validLatitude);
        params.put("validLongitude", this.validLongitude);
        params.put("latitude", this.latitude);
        params.put("longitude", this.longitude);
        params.put("maxAllowDistance", this.maxAllowDistance);
        params.put("usedWifi", this.usedWifi ? 1 : 0);
        params.put("ip", this.ip);
        params.put("image", this.image);
        params.put("reason", this.reason);
        params.put("approver", this.approver);
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
