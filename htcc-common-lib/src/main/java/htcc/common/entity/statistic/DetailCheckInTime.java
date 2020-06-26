package htcc.common.entity.statistic;

import htcc.common.entity.checkin.CheckinModel;
import htcc.common.util.DateTimeUtil;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class DetailCheckInTime implements Serializable {
    // chi nhánh
    public String officeId;
    // tên ca
    public String shiftName = "";
    // thời gian ca
    public String shiftTime = "";
    public float shiftStartTime = 0.0f;
    public float shiftEndTime = 0.0f;
    // loại (1 = điểm danh vào/ 2 = ra)
    public int type;
    // hình thức (1 = tọa độ/ 2 = hình ảnh/ 3 = QRCode/ 4 = Form)
    public int subType;
    // thời gian điểm danh
    public long clientTime;
    public float clientTimeFloat = 0.0f;
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
        this.shiftStartTime = Float.parseFloat(String.format("%.2f", DateTimeUtil.timeToFloat(model.getShiftTime().getStartTime())));
        this.shiftEndTime = Float.parseFloat(String.format("%.2f", DateTimeUtil.timeToFloat(model.getShiftTime().getEndTime())));
        this.officeId = model.getOfficeId();
        this.type = model.getType();
        this.subType = model.getSubType();
        this.clientTime = model.getClientTime();
        this.clientTimeFloat = Float.parseFloat(String.format("%.2f", DateTimeUtil.timeToFloat(
                DateTimeUtil.parseTimestampToString(model.getClientTime(), "HH:mm"))));
        this.isOnTime = model.isOnTime();
        this.image = model.getImage();
    }
}
