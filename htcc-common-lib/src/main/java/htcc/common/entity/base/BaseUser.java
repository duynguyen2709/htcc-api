package htcc.common.entity.base;

import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.AdminUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class BaseUser implements Serializable {

    private static final long serialVersionUID = 1327777583005150807L;

    public int    clientId = 1;
    public String username = "";
    public String password = "";
    public int    status   = 1;

    //extra info
    public String companyId   = "";
    public String email       = "";
    public String phoneNumber = "";

    public BaseUser(CompanyUserModel model) {
        this.username = model.username;
        this.password = model.password;
        this.companyId = model.companyId;
        this.email = model.email;
        this.phoneNumber = model.phoneNumber;
    }

    public BaseUser(AdminUser model){
        this.username = model.username;
        this.password = model.password;
        this.email = model.email;
        this.phoneNumber = model.phoneNumber;
    }
}
