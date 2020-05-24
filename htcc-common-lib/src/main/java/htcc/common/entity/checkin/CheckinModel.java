package htcc.common.entity.checkin;

import htcc.common.component.LoggingConfiguration;
import htcc.common.constant.CheckinSubTypeEnum;
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

    public String qrCodeId = "";

    public String reason = "";

    public String approver = "";

    public int status = 1;

    @NotEmpty
    public String checkInId = "";

    @NotEmpty
    public String companyId;

    @NotEmpty
    public String officeId;

    @NotEmpty
    public String username;

    @Min(1)
    @Max(2)
    public int type;

    @Min(1)
    @Max(4)
    public int subType;

    @Min(0)
    public long clientTime;

    @Min(0)
    public long serverTime;

    @NotEmpty
    public String validTime = "";

    public boolean isOnTime = true;

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

    public String image = "";

    @NotEmpty
    @Size(min = 8, max = 8)
    public String date;

    @Transient
    public String isValid(){

        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(officeId)) {
            return "Mã chi nhánh không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên người dùng không được rỗng";
        }

        if (CheckinSubTypeEnum.fromInt(subType) == null){
            return "Cách thức điểm danh không hợp lệ";
        }

        if (subType != CheckinSubTypeEnum.QR_CODE.getValue() &&
                CheckinTypeEnum.fromInt(type) == null) {
            return "Loại điểm danh không hợp lệ";
        }

        if (subType == CheckinSubTypeEnum.FORM.getValue() &&
                StringUtil.isEmpty(reason)) {
            return "Lý do không được rỗng";
        }

        if (usedWifi && !StringUtil.isIPAddress(StringUtil.valueOf(ip))) {
            return String.format("IP %s không hợp lệ", ip);
        }

        return StringUtil.EMPTY;
    }


    public CheckinModel(CheckinRequest request, long serverTime) {
        this.requestId = LoggingConfiguration.getTraceId();

        this.companyId = request.companyId;
        this.officeId = request.officeId;
        this.username = request.username;
        this.type = request.type;
        this.clientTime = request.clientTime;
        this.latitude = request.latitude;
        this.longitude = request.longitude;
        this.usedWifi = request.usedWifi;
        this.ip = request.ip;
        this.image = StringUtil.EMPTY;
        this.serverTime = serverTime;
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
        this.reason = (request.reason == null) ? StringUtil.EMPTY : request.reason;

        this.checkInId = String.format("%s-%s-%s-%s-%s",
                (this.type == CheckinTypeEnum.CHECKIN.getValue() ? "CheckIn" : "CheckOut"),
                this.companyId, this.officeId, this.username, this.clientTime);
    }

    public CheckinModel(CheckInLogEntity model) {
        this.requestId = model.requestId;
        this.checkInId = model.checkInId;
        this.subType = model.subType;
        this.companyId = model.companyId;
        this.officeId = model.officeId;
        this.username = model.username;
        this.type = CheckinTypeEnum.CHECKIN.getValue();
        this.clientTime = model.clientTime;
        this.latitude = model.latitude;
        this.longitude = model.longitude;
        this.validTime = model.validTime;
        this.isOnTime = model.isOnTime;
        this.validLatitude = model.validLatitude;
        this.validLongitude = model.validLongitude;
        this.maxAllowDistance = model.maxAllowDistance;
        this.usedWifi = model.usedWifi;
        this.ip = model.ip;
        this.image = model.image;
        this.serverTime = model.serverTime;
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
        this.reason = model.reason;
        this.status = model.status;
        this.approver = model.approver;
    }

    public CheckinModel(CheckOutLogEntity model) {
        this.requestId = model.requestId;
        this.checkInId = model.checkInId;
        this.subType = model.subType;
        this.companyId = model.companyId;
        this.officeId = model.officeId;
        this.username = model.username;
        this.type = CheckinTypeEnum.CHECKOUT.getValue();
        this.clientTime = model.clientTime;
        this.latitude = model.latitude;
        this.longitude = model.longitude;
        this.validTime = model.validTime;
        this.isOnTime = model.isOnTime;
        this.validLatitude = model.validLatitude;
        this.validLongitude = model.validLongitude;
        this.maxAllowDistance = model.maxAllowDistance;
        this.usedWifi = model.usedWifi;
        this.ip = model.ip;
        this.image = model.image;
        this.serverTime = model.serverTime;
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
        this.reason = model.reason;
        this.status = model.status;
        this.approver = model.approver;
    }
}
