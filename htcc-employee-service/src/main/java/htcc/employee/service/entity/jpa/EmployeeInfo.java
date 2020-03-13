package htcc.employee.service.entity.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Data
@IdClass(EmployeeInfo.Key.class)
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin nhân viên")
public class EmployeeInfo extends BaseJPAEntity {

    @Id
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Id
    @ApiModelProperty(notes = "Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Mã nhân viên",
                      example = "VNG-00001")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String employeeId = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String officeId = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Phòng ban/ bộ phận làm việc",
                      example = "PMA")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String department = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Họ tên",
                      example = "NGUYỄN ANH DUY")
    public String fullName = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Ngày sinh, định dạng (yyyy-MM-dd)",
                      example = "1998-09-27")
    public Date birthDate;

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Email",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Số CMND",
                      example = "123456789")
    public String identityCardNo = "";

    @Column
    @NotEmpty
    @Size(min = 10, max = 20, message = "Số điện thoại, ít nhất 10 chữ số")
    @ApiModelProperty(notes = "Sđt, ít nhất 10 chữ số",
                      example = "094822709")
    public String phoneNumber = "";

    @Column
    @NotEmpty
    @ApiModelProperty(notes = "Địa chỉ nơi ở",
                      example = "abc quận 7")
    public String address = "";

    @Column
    @ApiModelProperty(notes = "URL Ảnh đại diện (Default - không cần set)")
    public String avatar = "";

    @Override
    public boolean isValid() {
        return (StringUtil.valueOf(phoneNumber).length() >= 10);
    }

    public String getBirthDate(){
        return DateTimeUtil.parseDateToString(this.birthDate);
    }

    public void setBirthDate(String str) {
        this.birthDate = DateTimeUtil.parseStringToDate(str);
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
