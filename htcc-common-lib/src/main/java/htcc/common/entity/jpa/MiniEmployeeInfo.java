package htcc.common.entity.jpa;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MiniEmployeeInfo implements Serializable {
    private static final long serialVersionUID = 2L;

    private String username = "";
    private String fullName = "";
    private String email = "";
    private String phoneNumber = "";
    private String avatar = "";

    public MiniEmployeeInfo(EmployeeInfo entity) {
        this.username = entity.getUsername();
        this.fullName = entity.getFullName();
        this.email = entity.getEmail();
        this.phoneNumber = entity.getPhoneNumber();
        this.avatar = entity.getAvatar();
    }
}
