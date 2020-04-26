package htcc.common.entity.checkin;

import htcc.common.constant.CheckinTypeEnum;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Thông tin điểm danh")
public class CheckinResponse implements Serializable {

    private static final long serialVersionUID = 5926468583005150709L;

    @ApiModelProperty(notes = "Ngày gửi request")
    public String date = "";

    @ApiModelProperty(notes = "Danh sách chi nhánh cho phép điểm danh")
    public List<OfficeDetail> officeList = new ArrayList<>();

    @ApiModelProperty(notes = "Chi tiết các lần điểm danh trong ngày (vào/ra, thời gian, trễ)")
    public List<DetailCheckinTime> detailCheckinTimes = new ArrayList<>();

    public CheckinResponse(String yyyyMMdd) {
        this.date = yyyyMMdd;
        this.officeList = new ArrayList<>();
        this.detailCheckinTimes = new ArrayList<>();
    }



    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(description = "Chi tiết thời gian điểm danh")
    public static class DetailCheckinTime implements Serializable {

        private static final long serialVersionUID = 59264685830051509L;

        @ApiModelProperty(notes = "Loại điểm danh (1: Vào / 2: Ra)", example = "1")
        public int type = CheckinTypeEnum.CHECKIN.getValue();

        @ApiModelProperty(notes = "Giờ thực hiện điểm danh vào/ra", example = "17:35")
        public String time = "";

        @ApiModelProperty(notes = "Có sớm/trễ hay không", example = "true")
        public Boolean isOnTime = true;

        public DetailCheckinTime(CheckinModel checkinModel) {
            this.type = checkinModel.getType();
            this.time = DateTimeUtil.parseTimestampToString(checkinModel.getClientTime(), "HH:mm");
            this.isOnTime = checkinModel.isOnTime();
        }
    }




    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ApiModel(description = "Chi tiết thông tin các chi nhánh")
    public static class OfficeDetail implements Serializable {

        private static final long serialVersionUID = 5926468121230051509L;

        @ApiModelProperty(notes = "Mã chi nhánh", example = "CAMPUS")
        public String officeId = "";

        @ApiModelProperty(notes = "Có thể điểm danh hôm nay không (trừ ngày nghỉ)")
        public boolean canCheckin = true;

        @ApiModelProperty(notes = "Chỉ được dùng wifi")
        public boolean forceUseWifi = false;

        @ApiModelProperty(notes = "IP Subnet của mạng wifi cho phép")
        public String allowWifiIP = "";

        @ApiModelProperty(notes = "Khoảng cách cho phép thực hiện điểm danh (theo m)", example = "10")
        public long maxAllowDistance = 100000;

        @ApiModelProperty(notes = "Tọa độ cho phép thực hiện điểm danh", example = "10.762462")
        public float validLatitude = 10.762462f;

        @ApiModelProperty(notes = "Tọa độ cho phép thực hiện điểm danh", example = "106.682752")
        public float validLongitude = 106.682752f;
    }
}
