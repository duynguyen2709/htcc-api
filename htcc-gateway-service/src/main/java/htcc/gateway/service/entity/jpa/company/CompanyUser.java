package htcc.gateway.service.entity.jpa.company;

import htcc.common.entity.companyuser.CompanyUserModel;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.NumberUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@IdClass(CompanyUser.Key.class)
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CompanyUser extends BaseJPAEntity {

    public CompanyUser(CompanyUserModel model) {
        this.companyId = model.companyId;
        this.username = model.username;
        this.password = model.password;
        this.email = model.email;
        this.phoneNumber = model.phoneNumber;
        this.status = model.status;
    }

    public CompanyUserModel fromEntity() {
        CompanyUserModel model = new CompanyUserModel();
        model.companyId = this.companyId;
        model.username = this.username;
        model.email = this.email;
        model.phoneNumber = this.phoneNumber;
        model.status = this.status;
        model.password = null;
        return model;
    }

    @Id
    @NotEmpty
    public String companyId = "";

    @Id
    @NotEmpty
    public String username = "";

    @Column
    @NotEmpty
    @Size(min = 6, message = "Mật khẩu ít nhất 6 kí tự")
    public String password = "";

    @Column
    @NotEmpty
    @Email(message = "Không đúng định dạng email")
    public String email = "";

    @Column
    @NotEmpty
    @Size(min = 10, max = 20, message = "Số điện thoại, ít nhất 10 chữ số")
    public String phoneNumber = "";

    @Column
    public int role = 0;

    @Column
    public int status = 1;

    @Override
    public String isValid() {

        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
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
