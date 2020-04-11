package htcc.common.entity.checkin;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.CheckinTypeEnum;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.beans.Transient;
import java.io.Serializable;

@Data
@Log4j2
public class CheckinModel implements Serializable {

    private static final long serialVersionUID = 5926468583125150708L;

    @NotEmpty
    private String requestId = LoggingConfiguration.getTraceId();

    @NotEmpty
    public String companyId;

    @NotEmpty
    public String username;

    @Min(1)
    @Max(2)
    public int type;

    @Min(0)
    public long clientTime;

    @Min(0)
    public long serverTime;

    @NotEmpty
    public String validTime = "";

    @Min(0)
    public float latitude;

    @Min(0)
    public float longitude;

    @Min(0)
    public float validLatitude = 10.762462f;

    @Min(0)
    public float validLongitude = 106.682752f;

    @Min(0)
    public int maxAllowDistance = 10;

    public boolean usedWifi = false;

    public String ip = "";

    @NotEmpty
    @Size(min = 8, max = 8)
    public String date;

    @Transient
    public String isValid(){
        if (StringUtil.valueOf(date).isEmpty()) {
            return String.format("Thời gian gửi request {%s} không hợp lệ", this.clientTime);
        }

        if (type != CheckinTypeEnum.CHECKIN.getValue() &&
                type != CheckinTypeEnum.CHECKOUT.getValue()) {
            return "Loại điểm danh không hợp lệ";
        }

        if (usedWifi && !StringUtil.isIPAddress(StringUtil.valueOf(ip))) {
            return String.format("IP %s không hợp lệ", ip);
        }

        return StringUtil.EMPTY;
    }


    public CheckinModel(CheckinRequest request) {
        this.requestId = LoggingConfiguration.getTraceId();

        this.companyId = request.companyId;
        this.username = request.username;
        this.type = request.type;
        this.clientTime = request.clientTime;
        this.latitude = request.latitude;
        this.longitude = request.longitude;
//        this.validTime = request.validTime;
//        this.validLatitude = request.validLatitude;
//        this.validLongitude = request.validLongitude;
//        this.maxAllowDistance = request.maxAllowDistance;
        this.usedWifi = request.usedWifi;
        this.ip = request.ip;
        this.serverTime = System.currentTimeMillis();
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
    }

    public CheckinModel(CheckInLogEntity model) {
        this.requestId = model.requestId;
        this.companyId = model.companyId;
        this.username = model.username;
        this.type = CheckinTypeEnum.CHECKIN.getValue();
        this.clientTime = model.clientTime;
        this.latitude = model.latitude;
        this.longitude = model.longitude;
        this.validTime = model.validTime;
        this.validLatitude = model.validLatitude;
        this.validLongitude = model.validLongitude;
        this.maxAllowDistance = model.maxAllowDistance;
        this.usedWifi = model.usedWifi;
        this.ip = model.ip;
        this.serverTime = model.serverTime;
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
    }

    public CheckinModel(CheckOutLogEntity model) {
        this.requestId = model.requestId;
        this.companyId = model.companyId;
        this.username = model.username;
        this.type = CheckinTypeEnum.CHECKOUT.getValue();
        this.clientTime = model.clientTime;
        this.latitude = model.latitude;
        this.longitude = model.longitude;
        this.validTime = model.validTime;
        this.validLatitude = model.validLatitude;
        this.validLongitude = model.validLongitude;
        this.maxAllowDistance = model.maxAllowDistance;
        this.usedWifi = model.usedWifi;
        this.ip = model.ip;
        this.serverTime = model.serverTime;
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
    }
}
