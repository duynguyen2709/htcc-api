package htcc.gateway.service.entity.jpa;

import constant.AccountStatusEnum;
import entity.jpa.BaseJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
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
    public String email = "";

    @Column
    public int role = 0;

    @Column
    public int status = 1;

    @Override
    public boolean isValid() {
        return (AccountStatusEnum.fromInt(status) != null);
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
