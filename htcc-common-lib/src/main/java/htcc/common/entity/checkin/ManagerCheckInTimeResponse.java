package htcc.common.entity.checkin;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ManagerCheckInTimeResponse implements Serializable {

    private Map<String, Map<String, List<ManagerDetailCheckInTime>>> detailMap = new HashMap<>();
}
