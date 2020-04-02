package htcc.gateway.service.entity.jpa.company;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(CompanyUser.Key.class)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanyUser extends BaseJPAEntity {

    @Id
    public String companyId = "";

    @Id
    public String username = "";

    @Column
    public String password = "";

    @Column
    public int role = 0;

    @Column
    public int status = 1;

    @Override
    public String isValid() {
        if (AccountStatusEnum.fromInt(status) == null) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        return StringUtil.EMPTY;
    }

    public String getKey(){
        return companyId + "_" + username;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String companyId;
        public String username;

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
            Key that = (Key) o;
            return (companyId.equalsIgnoreCase(that.companyId) &&
                    username.equalsIgnoreCase(that.username));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, username);
        }
    }
}
