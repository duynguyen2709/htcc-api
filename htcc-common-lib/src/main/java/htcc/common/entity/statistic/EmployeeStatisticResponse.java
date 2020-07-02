package htcc.common.entity.statistic;

import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transient
    public Set<String> totalWorkingDaysSet = new HashSet<>();

    @Transient
    public Set<String> actualWorkingDaysSet = new HashSet<>();

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
