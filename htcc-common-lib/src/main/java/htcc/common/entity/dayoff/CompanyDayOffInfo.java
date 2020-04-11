package htcc.common.entity.dayoff;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
public class CompanyDayOffInfo implements Serializable {

    public boolean allowCancelRequest = true;

    public int maxDayAllowCancel = 1;

    public Map<String, Boolean> leavingRequestCategoryList = new HashMap<>();

    public Map<Float, Float> dayOffByLevel = new HashMap<>();
}
