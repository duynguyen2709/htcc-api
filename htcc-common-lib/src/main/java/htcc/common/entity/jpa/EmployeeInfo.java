package htcc.common.entity.jpa;

import com.fasterxml.jackson.annotation.JsonProperty;
import htcc.common.util.DateTimeUtil;
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
@ApiModel(description = "Thông tin nhân viên (các field * không cần gửi lại khi update)\n" +
          "(các field còn lại phải gửi lại kể cả khi không update)")
public class EmployeeInfo extends BaseJPAEntity {

    private static final long serialVersionUID = 5922818583125150708L;

    @Id
    @ApiModelProperty(notes = "(*) Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Id
    @ApiModelProperty(notes = "(*) Tên đăng nhập",
                      example = "admin")
    public String username = "";

    @Column
    @ApiModelProperty(notes = "(*) Mã nhân viên",
                      example = "VNG-00001")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String employeeId = "";

    @Column
    @ApiModelProperty(notes = "(*) Mã chi nhánh",
                      example = "CAMPUS")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String officeId = "";

    @Column
    @ApiModelProperty(notes = "(*) Phòng ban/ bộ phận làm việc",
                      example = "PMA")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String department = "";

    @Column
    @ApiModelProperty(notes = "(*) Chức danh/ Cấp độ nhân viên",
                      example = "Junior Developer")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    public String title = "";

    @Column
    @ApiModelProperty(notes = "Họ tên",
                      example = "NGUYỄN ANH DUY")
    public String fullName = "";

    @Column
    @ApiModelProperty(notes = "Ngày sinh, định dạng (yyyy-MM-dd)",
                      example = "1998-09-27")
    public Date birthDate;

    @Column
    @ApiModelProperty(notes = "Email",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @Column
    @ApiModelProperty(notes = "Số CMND",
                      example = "012345678")
    public String identityCardNo = "";

    @Column
    @ApiModelProperty(notes = "Sđt, ít nhất 10 chữ số",
                      example = "0948202709")
    public String phoneNumber = "";

    @Column
    @ApiModelProperty(notes = "Địa chỉ nơi ở",
                      example = "Quận 7, TPHCM")
    public String address = "";

    @Column
    @ApiModelProperty(notes = "(*) URL Ảnh đại diện (Default - không cần set)")
    public String avatar = "";

    @Override
    public String isValid() {

        if (StringUtil.isEmpty(fullName)) {
            return "Họ tên không được rỗng";
        }

        if (StringUtil.isEmpty(address)) {
            return "Địa chỉ không được rỗng";
        }

        if (birthDate == null) {
            return "Ngày sinh sai định dạng yyyy-MM-dd";
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
        }

        if (StringUtil.valueOf(identityCardNo).length() < 9 ||
                NumberUtil.getLongValue(identityCardNo) == 0L) {
            return String.format("Số chứng minh nhân dân %s không phù hợp định dạng", identityCardNo);
        }

        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        return StringUtil.EMPTY;
    }

    public void refillImmutableValue(EmployeeInfo other){
        this.username = other.username;
        this.companyId = other.companyId;
        this.employeeId = other.employeeId;
        this.officeId = other.officeId;
        this.department = other.department;
        this.title = other.title;
        this.avatar = other.avatar;
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
