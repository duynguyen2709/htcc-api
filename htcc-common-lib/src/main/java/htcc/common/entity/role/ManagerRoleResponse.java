package htcc.common.entity.role;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
public class ManagerRoleResponse implements Serializable {

    private static final long serialVersionUID = 140L;

    private List<ManagerRole> roleList = new ArrayList<>();
    private Map<String, Map<String, Boolean>> defaultRoleDetail = new HashMap<>();
}
