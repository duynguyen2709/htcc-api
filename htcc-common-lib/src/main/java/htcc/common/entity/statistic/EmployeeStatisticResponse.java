package htcc.common.entity.statistic;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.shift.ShiftTime;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@NoArgsConstructor
public class EmployeeStatisticResponse implements Serializable {

    // tỉ lệ checkin đúng giờ
    public float onTimePercentage = 0.0f;
    // số lần điểm danh đúng giờ
    public int onTimeCount = 0;
    // số lần thực hiện điểm danh
    public int checkinTimes = 0;
    // tổng số ngày phải đi làm
    public float totalDays = 0.0f;
    // số ngày đi làm thực tế
    public float workingDays = 0.0f;
    // số ngày nghỉ có phép
    public float validOffDays = 0.0f;
    // số ngày nghỉ không phép (= totalDays - workingDays - validOffDays)
    public float nonPermissionOffDays = 0.0f;
    // số giờ OT
    public float overtimeHours = 0.0f;
    // danh sách chi tiết theo ngày
    public List<DetailStatisticsByDate> detailList = new ArrayList<>();

    @Data
    @NoArgsConstructor
    public static class DetailStatisticsByDate implements Serializable {
        // ngày
        public String date = "";
        // chi tiết thời gian checkin
        public List<DetailCheckInTime> listCheckInTime = new ArrayList<>();
        // chi tiết thời gian nghỉ
        public List<DetailDayOff> listDayOff = new ArrayList<>();
    }

    @Data
    @NoArgsConstructor
    public static class DetailCheckInTime implements Serializable {
        // chi nhánh
        public String officeId;
        // tên ca
        public String shiftName = "";
        // thời gian ca
        public String shiftTime = "";
        // loại (1 = điểm danh vào/ 2 = ra)
        public int type;
        // hình thức (1 = tọa độ/ 2 = hình ảnh/ 3 = QRCode/ 4 = Form)
        public int subType;
        // thời gian điểm danh
        public long clientTime;
        // đúng giờ hay không
        public boolean isOnTime = true;
        // trạng thái (0: từ chối/ 1 = hợp lệ / 2 = chờ phê duyệt) (khi điểm danh form/ image cần phê duyệt)
        public int status = 1;
        // người phê duyệt (empty thì không hiện)
        public String approver = "";
        // ảnh (nếu điểm danh = image/ empty thì không hiện)
        public String image = "";
        // lý do (nếu điểm danh = form /empty thì không hiện)
        public String reason = "";

        public DetailCheckInTime(CheckinModel model) {
            this.reason = model.getReason();
            this.approver = model.getApprover();
            this.status = model.getStatus();
            this.shiftName = model.getShiftTime().getShiftName();
            this.shiftTime = String.format("%s - %s", model.getShiftTime().getStartTime(), model.getShiftTime().getEndTime());
            this.officeId = model.getOfficeId();
            this.type = model.getType();
            this.subType = model.getSubType();
            this.clientTime = model.getClientTime();
            this.isOnTime = model.isOnTime();
            this.image = model.getImage();
        }
    }

    @Data
    @NoArgsConstructor
    public static class DetailDayOff implements Serializable {
        // loại nghỉ phép (nghỉ phép năm, nghỉ bênh...)
        public String category;
        // buổi (0 = cả ngày / 1 = buổi sáng / 2 = buổi chiều)
        public int session;

        public DetailDayOff(LeavingRequestModel model, String date) {
            this.category = model.getCategory();
            for (LeavingRequest.LeavingDayDetail detail : model.getDetail()) {
                if (detail.getDate().equals(date)) {
                    this.session = detail.getSession();
                }
            }
        }
    }
}
