package htcc.common.entity.jpa;

import com.google.gson.reflect.TypeToken;
import htcc.common.constant.AccountStatusEnum;
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
import java.util.List;

@Entity
@Data
@RequiredArgsConstructor
@AllArgsConstructor
@ApiModel(description = "Thông tin công ty")
public class Company extends BaseJPAEntity {

    private static final long serialVersionUID = 1326968583005150807L;

    @Id
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Column
    @ApiModelProperty(notes = "Tên công ty",
                      example = "Công ty cổ phần VNG")
    public String companyName = "";

    @Column
    @ApiModelProperty(notes = "Email",
                      example = "naduy.hcmus@gmail.com")
    public String email = "";

    @Column
    @ApiModelProperty(notes = "Sđt, ít nhất 10 chữ số",
                      example = "0948202709")
    public String phoneNumber = "";

    @Column
    @ApiModelProperty(notes = "Địa chỉ công ty",
                      example = "Quận 7, TPHCM")
    public String address = "";

    @Column
    @ApiModelProperty(notes = "Trạng thái công ty (1: Active / 0: Blocked)",
                      example = "1")
    public int status = 1;

    public String supportedScreens = "";
//
//    public List<Integer> getSupportedScreens() {
//        return StringUtil.json2Collection(this.supportedScreens,
//                new TypeToken<List<Integer>>() {}.getType());
//    }
//
//    public void setSupportedScreens(List<Integer> list) {
//        this.supportedScreens = StringUtil.toJsonString(list);
//    }

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.valueOf(phoneNumber).length() < 10 ||
                NumberUtil.getLongValue(phoneNumber) == 0L) {
            return String.format("Số điện thoại %s không phù hợp định dạng", phoneNumber);
        }

        if (StringUtil.isEmpty(companyName)) {
            return "Tên công ty không được rỗng";
        }

        if (StringUtil.isEmpty(address)) {
            return "Địa chỉ công ty không được rỗng";
        }

        if (!StringUtil.isEmail(email)) {
            return String.format("Email %s không phù hợp định dạng email", email);
        }

        if (AccountStatusEnum.fromInt(status) == null) {
            return String.format("Trạng thái %s không hợp lệ", status);
        }

        return StringUtil.EMPTY;
    }
}
