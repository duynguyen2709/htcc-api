package htcc.common.entity.dayoff;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
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
public class CompanyDayOffInfo extends BaseJPAEntity implements Serializable {

    private static final long serialVersionUID = 926270983005150708L;

    public boolean allowCancelRequest = true;

    public int maxDayAllowCancel = 0;

    public List<CategoryList> categoryList = new ArrayList<>();

    public Map<Float, Float> dayOffByLevel = new HashMap<>();

    @Override
    public String isValid() {
        if (categoryList.isEmpty()) {
            return "Danh sách loại nghỉ phép không được rỗng";
        }

        if (dayOffByLevel.isEmpty()) {
            return "Danh sách số ngày nghỉ phép theo cấp bậc không được rỗng";
        }

        for (Map.Entry<Float, Float> entry : dayOffByLevel.entrySet()){
            if (entry.getKey() < 0 || entry.getValue() < 0) {
                return "Giá trị cấp bậc/ Số ngày nghỉ không được âm";
            }
        }

        for (CategoryList category : categoryList) {
            if (category.getCategory().isEmpty()) {
                return "Loại nghỉ phép không được rỗng";
            }
        }

        return StringUtil.EMPTY;
    }

    @Data
    @AllArgsConstructor
    @RequiredArgsConstructor
    public static class CategoryList implements Serializable {
        public String  category = "";
        public boolean useDayOff = true;
        public boolean hasSalary = false;
    }
}
