package htcc.common.entity.shift;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class EmployeeShiftDetail implements Serializable {

    private static final long serialVersionUID = 5922818519L;

    public Map<Integer, List<MiniShiftTime>> fixedShiftMap = new HashMap<>();
    public Map<String, List<MiniShiftTime>> shiftByDateMap = new HashMap<>();
}
