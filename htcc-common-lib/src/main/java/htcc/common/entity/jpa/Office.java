package htcc.common.entity.jpa;

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
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@IdClass(Office.Key.class)
@ApiModel(description = "Thông tin chi nhánh")
public class Office extends BaseJPAEntity {

    private static final long serialVersionUID = 5922818583125150708L;

    @Id
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Id
    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    public String officeId = "";

    @Column
    @ApiModelProperty(notes = "Tên chi nhánh",
                      example = "abc")
    public String officeName = "";

    @Column
    @ApiModelProperty(notes = "Địa chỉ",
                      example = "Quận 7, TPHCM")
    public String address = "";

    @Column
    @ApiModelProperty(notes = "Kinh độ",
                      example = "10.762462")
    public float latitude = 0.0f;

    @Column
    @ApiModelProperty(notes = "Vĩ độ",
                      example = "106.682752")
    public float longitude = 0.0f;

    @Column
    @ApiModelProperty(notes = "Khoảng cách tối đa cho phép điểm danh (tính theo m)",
                      example = "10")
    public long maxAllowDistance = 10;

    @Column
    @ApiModelProperty(notes = "SĐT liên hệ chi nhánh",
                      example = "0948202709")
    public String phoneNumber = "";

    @Column
    @ApiModelProperty(notes = "Email liên hệ chi nhánh",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @Column
    @ApiModelProperty(notes = "Bắt buộc sử dụng wifi khi điểm danh",
                      example = "false")
    public boolean forceUseWifi = false;

    @Column
    @ApiModelProperty(notes = "Địa chỉ IP subnet cho phép điểm danh")
    public String allowWifiIP = "";

    @Column
    @ApiModelProperty(notes = "Có phải trụ sở chính hay không",
                      example = "false")
    public Boolean isHeadquarter = false;

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(officeId)) {
            return "Mã chi nhánh không được rỗng";
        }

        if (StringUtil.isEmpty(officeName)) {
            return "Tên chi nhánh không được rỗng";
        }

        if (StringUtil.isEmpty(address)) {
            return "Địa chỉ không được rỗng";
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
        }

        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String companyId;
        public String officeId;

        public Key(String key){
            this.companyId = key.split("_")[0];
            this.officeId = key.split("_")[1];
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
                    officeId.equalsIgnoreCase(that.officeId));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, officeId);
        }
    }
}
