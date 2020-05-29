package htcc.common.entity.notification;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Objects;

/**
 * JPA Entity for saving notification token push
 */
@Entity
@IdClass(NotificationBuz.Key.class)
@Data
public class NotificationBuz extends BaseJPAEntity {

    @Id
    @Min(1)
    @Max(3)
    public int clientId = 1;

    @Id
    @NotEmpty
    public String companyId = "";

    @Id
    @NotEmpty
    public String username = "";

    @Column
    @NotEmpty
    public String tokens = "";

    @Column
    @Min(0)
    @Max(1)
    public int isLoggedIn = 1;

    @Override
    public String isValid() {
        return StringUtil.EMPTY;
    }

    public Key getKey(){
        return new Key(clientId, companyId, username);
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public int    clientId;
        public String companyId;
        public String username;

        public Key(String key){
            this.clientId = Integer.parseInt(key.split("_")[0]);
            this.companyId = key.split("_")[1];
            this.username = key.split("_")[2];
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
            return (clientId == that.clientId &&
                    companyId.equalsIgnoreCase(that.companyId) &&
                    username.equalsIgnoreCase(that.username));
        }

        @Override
        public int hashCode() {
            return Objects.hash(clientId, companyId, username);
        }
    }
}
