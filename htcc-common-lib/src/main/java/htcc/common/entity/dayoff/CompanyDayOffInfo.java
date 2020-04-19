package htcc.common.entity.dayoff;

import htcc.common.constant.Constant;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Thông tin ngày nghỉ phép")
public class CompanyDayOffInfo extends BaseJPAEntity implements Serializable {

    private static final long serialVersionUID = 926270983005150708L;

    @ApiModelProperty(notes = "Cho phép hủy đơn nghỉ phép",
                      example = "true")
    public boolean allowCancelRequest = true;

    @ApiModelProperty(notes = "Số ngày có thể hủy trước khi đơn có hiệu lực " +
            "\n(VD : config là 1 thì chỉ cho phép hủy đơn trước từ 2 ngày trở lên)",
                      example = "0")
    public int maxDayAllowCancel = 0;

    @ApiModelProperty(notes = "Danh sách loại nghỉ phép")
    public List<CategoryEntity> categoryList = new ArrayList<>();

    @ApiModelProperty(notes = "Số ngày nghỉ cho phép theo cấp bậc nhân viên")
    public List<DayOffByLevelEntity> dayOffByLevel = new ArrayList<>();

    @Override
    public String isValid() {
        if (categoryList.isEmpty()) {
            return "Danh sách loại nghỉ phép không được rỗng";
        }

        if (dayOffByLevel.isEmpty()) {
            return "Danh sách số ngày nghỉ phép theo cấp bậc không được rỗng";
        }

        boolean hasDefaultDayOff = false;
        for (DayOffByLevelEntity entry : dayOffByLevel){
            if (entry.getTotalDayOff() < 0 || entry.getLevel() < 0) {
                return "Giá trị cấp bậc/ Số ngày nghỉ không được âm";
            }

            if (entry.getLevel() == 0.0f){
                hasDefaultDayOff = true;
            }
        }

        if (!hasDefaultDayOff){
            return "Thiếu giá trị ngày nghỉ mặc định (cấp bậc 0.0)";
        }

        for (CategoryEntity category : categoryList) {
            if (category.getCategory().isEmpty()) {
                return "Loại nghỉ phép không được rỗng";
            }
        }

        return StringUtil.EMPTY;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    @ApiModel(description = "Thông tin loại nghỉ phép")
    public static class CategoryEntity implements Serializable {
        @ApiModelProperty(notes = "Tên loại",
                          example = "Nghỉ phép năm")
        public String  category = "";

        @ApiModelProperty(notes = "Có trừ ngày phép hay không",
                          example = "true")
        public boolean useDayOff = true;

        @ApiModelProperty(notes = "Có được hưởng lương hay không",
                          example = "false")
        public boolean hasSalary = false;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    @ApiModel(description = "Số ngày nghỉ phép theo cấp bậc")
    public static class DayOffByLevelEntity implements Serializable {
        @ApiModelProperty(notes = "Cấp bậc",
                          example = "1.1")
        public float level = 0.0f;

        @ApiModelProperty(notes = "Số ngày nghỉ phép",
                          example = "10.0")
        public float totalDayOff = Constant.DEFAULT_TOTAL_DAY_OFF;
    }
}
