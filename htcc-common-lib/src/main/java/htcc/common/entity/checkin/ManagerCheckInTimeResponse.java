package htcc.common.entity.checkin;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@ApiModel("Lịch sử điểm danh")
public class ManagerCheckInTimeResponse implements Serializable {

    @ApiModelProperty("{username: {date: [list_detail_checkin_time]}}")
    private Map<String, Map<String, List<ManagerDetailCheckInTime>>> detailMap = new HashMap<>();
}
