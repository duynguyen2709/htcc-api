package htcc.employee.service.entity.checkin;

import htcc.common.constant.CheckinTypeEnum;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
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
    public String companyId;

    @NotEmpty
    public String username;

    @Min(1)
    @Max(2)
    public int type;

    @Min(0)
    public long clientTime;

    @Min(0)
    public float latitude;

    @Min(0)
    public float longitude;

    @Min(0)
    public float validLatitude;

    @Min(0)
    public float validLongitude;

    @Min(0)
    public int minAllowDistance = 10;

    public boolean usedWifi = false;

    public String ip = "";

    @Min(0)
    public long serverTime;

    @NotEmpty
    @Size(min = 8, max = 8)
    public String date;

    @Transient
    public String isValid(){
        if (StringUtil.valueOf(date).isEmpty()) {
            return String.format("Thời gian gửi request {%s} không hợp lệ", this.clientTime);
        }

        if (Math.abs(serverTime - clientTime) > 5 * 60 * 1000) {
            return "Thời gian gửi request quá 5 phút";
        }

        if (type != CheckinTypeEnum.CHECKIN.getValue() &&
                type != CheckinTypeEnum.CHECKOUT.getValue()) {
            return "Loại điểm danh không hợp lệ";
        }

        if (usedWifi && !StringUtil.isIPAddress(ip)) {
            return String.format("IP %s không hợp lệ", ip);
        }

        return StringUtil.EMPTY;
    }


    public CheckinModel(CheckinRequest request, int type) {
        this.companyId = request.companyId;
        this.username = request.username;
        this.type = type;
        this.clientTime = request.clientTime;
        this.latitude = request.latitude;
        this.longitude = request.longitude;
        this.validLatitude = request.validLatitude;
        this.validLongitude = request.validLongitude;
        this.minAllowDistance = request.minAllowDistance;
        this.usedWifi = request.usedWifi;
        this.ip = request.ip;
        this.serverTime = System.currentTimeMillis();
        this.date = DateTimeUtil.parseTimestampToString(this.clientTime,"yyyyMMdd");
    }
}