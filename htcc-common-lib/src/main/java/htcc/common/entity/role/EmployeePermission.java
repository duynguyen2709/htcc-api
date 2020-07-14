package htcc.common.entity.role;

import com.google.gson.reflect.TypeToken;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.*;

@Entity
@IdClass(EmployeePermission.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class EmployeePermission extends BaseJPAEntity {

    private static final long serialVersionUID = 141L;

    @Id
    private String companyId = "";
    @Id
    private String username = "";
    @Column
    private String lineManager = "";
    @Column
    private String subManagers = "";
    @Column
    private String subordinates = "";
    @Column
    private String canManageOffices = "";
    @Column
    private String canManageDepartments = "";
    @Column
    private String managerRole = "";

    public List<String> getSubManagers() {
        return StringUtil.json2Collection(subManagers, new TypeToken<List<String>>() {}.getType());
    }
    public void setSubManagers(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Set<String> set = new HashSet<>(list);
        this.subManagers = StringUtil.toJsonString(set);
    }

    public List<String> getSubordinates() {
        return StringUtil.json2Collection(subordinates, new TypeToken<List<String>>() {}.getType());
    }
    public void setSubordinates(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Set<String> set = new HashSet<>(list);
        this.subordinates = StringUtil.toJsonString(set);
    }

    public List<String> getCanManageOffices() {
        return StringUtil.json2Collection(canManageOffices, new TypeToken<List<String>>() {}.getType());
    }
    public void setCanManageOffices(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Set<String> set = new HashSet<>(list);
        this.canManageOffices = StringUtil.toJsonString(set);
    }

    public List<String> getCanManageDepartments() {
        return StringUtil.json2Collection(canManageDepartments, new TypeToken<List<String>>() {}.getType());
    }
    public void setCanManageDepartments(List<String> list) {
        if (list == null) {
            list = new ArrayList<>();
        }
        Set<String> set = new HashSet<>(list);
        this.canManageDepartments = StringUtil.toJsonString(set);
    }

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }
        if (StringUtil.isEmpty(username)) {
            return "Tên nhân viên không hợp lệ";
        }

        try {
            if (!StringUtil.isEmpty(subManagers)) {
                List<String> list = StringUtil.json2Collection(subManagers, new TypeToken<List<String>>() {
                }.getType());
                if (list == null) {
                    throw new Exception("null");
                }
            }
        } catch (Exception e) {
            log.error("parse subManagers ex", e);
            return "Danh sách quản lý phụ không hợp lệ";
        }

        try {
            if (!StringUtil.isEmpty(canManageOffices)) {
                List<String> list = StringUtil.json2Collection(canManageOffices, new TypeToken<List<String>>() {
                }.getType());
                if (list == null) {
                    throw new Exception("null");
                }
            }
        } catch (Exception e) {
            log.error("parse canManageOffices ex", e);
            return "Danh sách chi nhánh quản lý không hợp lệ";
        }
        try {
            if (!StringUtil.isEmpty(canManageDepartments)) {
                List<String> list = StringUtil.json2Collection(canManageDepartments, new TypeToken<List<String>>() {
                }.getType());
                if (list == null) {
                    throw new Exception("null");
                }
            }
        } catch (Exception e) {
            log.error("parse canManageDepartments ex", e);
            return "Danh sách phòng ban quản lý không hợp lệ";
        }
        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        private String companyId;
        private String username;

        public Key(String key){
            this.companyId = key.split("_")[0];
            this.username = key.split("_")[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            EmployeePermission.Key that = (EmployeePermission.Key) o;
            return (companyId.equalsIgnoreCase(that.companyId) &&
                    username.equalsIgnoreCase(that.username));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, username);
        }
    }
}
