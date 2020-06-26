package htcc.common.entity.checkin;

import htcc.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ManagerDetailCheckInTime implements Serializable {
    // mã điểm danh
    public String checkInId = "";
    // Tên nhân viên
    public String username = "";
    // chi nhánh
    public String officeId = "";
    // tên ca
    public String shiftName = "";
    // thời gian ca
    public String shiftTime = "";
    // loại (1 = điểm danh vào/ 2 = ra)
    public int type = 1;
    // giờ điểm danh
    public String checkInTime = "";
    // ngày điểm danh
    public String checkInDate = "";
    // đúng giờ hay không
    public Boolean isOnTime = true;
    // người phê duyệt (empty thì không hiện)
    public String approver = "";
    // ảnh (nếu điểm danh = image/ empty thì không hiện)
    public String image = "";
    // lý do (nếu điểm danh = form /empty thì không hiện)
    public String reason = "";
    // trạng thái đơn điểm danh
    public int status = 2;

    public ManagerDetailCheckInTime(CheckinModel model) {
        this.checkInId = model.getCheckInId();
        this.username = model.getUsername();
        this.officeId = model.getOfficeId();
        this.shiftName = model.getShiftTime().getShiftName();
        this.shiftTime = String.format("%s - %s", model.getShiftTime().getStartTime(), model.getShiftTime().getEndTime());
        this.type = model.getType();
        this.checkInTime = DateTimeUtil.parseTimestampToString(model.getClientTime(), "HH:mm");
        this.checkInDate = DateTimeUtil.parseTimestampToString(model.getClientTime(), "dd/MM/yyyy");
        this.isOnTime = model.isOnTime();
        this.approver = model.getApprover();
        this.image = model.getImage();
        this.reason = model.getReason();
        this.status = model.getStatus();
    }
}
