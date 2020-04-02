package htcc.gateway.service.entity.jpa.admin;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.StringUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

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
    public String isValid() {
        if (AccountStatusEnum.fromInt(status) == null) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        return StringUtil.EMPTY;
    }
}
