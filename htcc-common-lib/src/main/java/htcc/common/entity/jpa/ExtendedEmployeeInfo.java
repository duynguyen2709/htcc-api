package htcc.common.entity.jpa;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.IdClass;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin nhân viên")
public class ExtendedEmployeeInfo extends EmployeeInfo {

    @ApiModelProperty(notes = "Trạng thái tài khoản (0 = Blocked / 1 = Active)",
                      example = "1")
    public int status = 1;

    public ExtendedEmployeeInfo(EmployeeInfo entity) {
        this.companyId = entity.getCompanyId();
        this.username = entity.getUsername();
        this.employeeId = entity.getEmployeeId();
        this.officeId = entity.getOfficeId();
        this.department = entity.getDepartment();
        this.title = entity.getTitle();
        this.fullName = entity.getFullName();
        this.gender = entity.getGender();
        this.birthDate = entity.birthDate;
        this.email = entity.getEmail();
        this.identityCardNo = entity.getIdentityCardNo();
        this.phoneNumber = entity.getPhoneNumber();
        this.address = entity.getAddress();
        this.avatar = entity.getAvatar();
    }
}
