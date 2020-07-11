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
import java.util.Map;
import java.util.Objects;

@Entity
@IdClass(ManagerRole.Key.class)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class ManagerRole extends BaseJPAEntity {

    private static final long serialVersionUID = 139L;

    @Id
    @NotEmpty
    private String companyId = "";
    @Id
    @NotEmpty
    private String roleId = "";
    @Column
//    @NotEmpty
    private String roleName = "";
    @Column
    @NotEmpty
    private String roleDetail = "";

    public Map<String, Map<String, Boolean>> getRoleDetail() {
        return StringUtil.json2Collection(roleDetail,
                new TypeToken<Map<String, Map<String, Boolean>>>() {}.getType());
    }

    public void setRoleDetail(Map<String, Map<String, Boolean>> map) {
        this.roleDetail = StringUtil.toJsonString(map);
    }

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không hợp lệ";
        }
        if (StringUtil.isEmpty(roleId)) {
            return "Mã nhóm quyền không hợp lệ";
        }
//        if (StringUtil.isEmpty(roleName)) {
//            return "Tên nhóm quyền không hợp lệ";
//        }
        try {
            Map<String, Map<String, Boolean>> map = StringUtil.json2Collection(roleDetail,
                    new TypeToken<Map<String, Map<String, Boolean>>>() {}.getType());
            if (map == null) {
                throw new Exception("null");
            }
        } catch (Exception e) {
            log.error("parse roleDetail ex", e);
            return "Chi tiết phân quyền không hợp lệ";
        }

        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        private String companyId;
        private String roleId;

        public Key(String key){
            this.companyId = key.split("_")[0];
            this.roleId = key.split("_")[1];
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            ManagerRole.Key that = (ManagerRole.Key) o;
            return (companyId.equalsIgnoreCase(that.companyId) &&
                    roleId.equalsIgnoreCase(that.roleId));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, roleId);
        }
    }
}
