package htcc.common.entity.checkin;

import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class ManagerDetailCheckInTime implements Serializable {
    // mã điểm danh
    @ApiModelProperty(notes = "Mã điểm danh",
                      example = "CAMPUS")
    public String checkInId = "";
    // Tên nhân viên
    @ApiModelProperty(notes = "Tên nhân viên",
                      example = "admin")
    public String username = "";
    // chi nhánh
    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    public String officeId = "";
    // tên ca
    @ApiModelProperty(notes = "tên ca",
                      example = "Ca 1")
    public String shiftName = "";
    // thời gian ca
    @ApiModelProperty(notes = "thời gian ca",
                      example = "08:30 - 17:30")
    public String shiftTime = "";
    // loại (1 = điểm danh vào/ 2 = ra)
    @ApiModelProperty(notes = "loại (1 = điểm danh vào/ 2 = tan ca)",
                      example = "1")
    public int type = 1;
    // giờ điểm danh
    @ApiModelProperty(notes = "giờ điểm danh",
                      example = "07:31")
    public String checkInTime = "";
    // ngày điểm danh
    @ApiModelProperty(notes = "ngày điểm danh",
                      example = "26/06/2020")
    public String checkInDate = "";
    // đúng giờ hay không
    @ApiModelProperty(notes = "đúng giờ hay không",
                      example = "true")
    public Boolean isOnTime = true;
    // người phê duyệt (empty thì không hiện)
    @ApiModelProperty(notes = "người phê duyệt (empty thì không hiện)",
                      example = "admin")
    public String approver = "";
    // ảnh (nếu điểm danh = image/ empty thì không hiện)
    @ApiModelProperty(notes = "URL ảnh (nếu điểm danh = image/ empty thì không hiện)")
    public String image = "";
    // lý do (nếu điểm danh = form /empty thì không hiện)
    @ApiModelProperty(notes = "lý do (empty thì không hiện)",
                      example = "Quên điểm danh")
    public String reason = "";
    // trạng thái đơn điểm danh
    @ApiModelProperty(notes = "trạng thái đơn điểm danh (0/1/2) (giống đơn nghỉ phép, khiếu nại)",
                      example = "1")
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
