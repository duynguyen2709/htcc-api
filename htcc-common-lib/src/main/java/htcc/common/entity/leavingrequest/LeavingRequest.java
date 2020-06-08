package htcc.common.entity.leavingrequest;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.constant.SessionEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Data
@ApiModel(description = "Request xin nghỉ phép")
public class LeavingRequest extends BaseJPAEntity {

    private static final long serialVersionUID = 5926270983005150708L;

    public int clientId = ClientSystemEnum.MOBILE.getValue();

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    @NotEmpty
    public String companyId;

    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    @NotEmpty
    public String username;

    @ApiModelProperty(notes = "Thời gian client gửi request: System.currentTimeMillis()",
                      example = "123")
    @Min(0)
    public long clientTime;

    @ApiModelProperty(notes = "Loại nghỉ phép (chọn từ list trả về)",
                      example = "Nghỉ phép năm")
    @NotEmpty
    public String category;

    @ApiModelProperty(notes = "Mô tả lý do nghỉ",
                      example = "Nghỉ bệnh")
    @NotEmpty
    public String reason;

    @ApiModelProperty(notes = "Chi tiết ngày nghỉ (buổi nào)")
    public List<LeavingDayDetail> detail = new ArrayList<>();

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(username)) {
            return "Tên người dùng không được rỗng";
        }

        if (StringUtil.isEmpty(companyId)) {
            return "Loại nghỉ phép không được rỗng";
        }

        if (StringUtil.isEmpty(reason)) {
            return "Lý do nghỉ phép không được rỗng";
        }

        if (detail == null || detail.isEmpty()) {
            return "Chi tiết ngày nghỉ không được rỗng";
        }

        for (LeavingDayDetail d : detail) {
            if (!DateTimeUtil.isRightFormat(d.date, "yyyyMMdd")) {
                return String.format("Ngày %s không phù hợp định dạng yyyyMMdd", d.date);
            }

            if (clientId == ClientSystemEnum.MOBILE.getValue() &&
                    DateTimeUtil.isBeforeToday(d.date)){
                return "Không được đăng ký ngày trước hôm nay";
            }

            if (SessionEnum.fromInt(d.session) == null) {
                return String.format("Buổi %s không hợp lệ", d.session);
            }
        }

        return StringUtil.EMPTY;
    }

    @ApiModel(description = "Chi tiết ngày nghỉ (buổi nào)")
    public static class LeavingDayDetail implements Serializable {

        private static final long serialVersionUID = 5926271083005150708L;

        @ApiModelProperty(notes = "Ngày nghỉ (yyyyMMdd)",
                          example = "20200405")
        public String date = "";

        @ApiModelProperty(notes = "Buổi nghỉ (0 : cả ngày/ 1: buổi sáng / 2: buổi chiều)",
                          example = "0")
        public int session = 0;

        public static Comparator<LeavingDayDetail> getComparator(){
            return new Comparator<LeavingDayDetail>() {
                @Override
                public int compare(LeavingDayDetail o1, LeavingDayDetail o2) {
                    Date d1 = DateTimeUtil.parseStringToDate(o1.date, "yyyyMMdd");
                    Date d2 = DateTimeUtil.parseStringToDate(o2.date, "yyyyMMdd");

                    return d1.compareTo(d2);
                }
            };
        }
    }
}
