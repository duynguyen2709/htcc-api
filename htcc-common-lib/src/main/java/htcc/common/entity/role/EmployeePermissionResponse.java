package htcc.common.entity.role;

import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.MiniEmployeeInfo;
import htcc.common.entity.jpa.Office;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class EmployeePermissionResponse implements Serializable {

    private static final long serialVersionUID = 142L;

    private DataView dataView = null;
    private EmployeePermission dataEdit = null;

    @Data
    @NoArgsConstructor
    public static class DataView {
        private MiniEmployeeInfo       lineManager;
        private List<MiniEmployeeInfo> subManagers;
        private List<MiniEmployeeInfo> subordinates;
        private List<Office>           canManageOffices;
        private List<Department>       canManageDepartments;
        private ManagerRole            managerRole;
    }
}
