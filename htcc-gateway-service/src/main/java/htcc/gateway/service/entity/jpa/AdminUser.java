package htcc.gateway.service.entity.jpa;

import constant.AccountStatusEnum;
import entity.jpa.BaseJPAEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class AdminUser extends BaseJPAEntity {

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
}
