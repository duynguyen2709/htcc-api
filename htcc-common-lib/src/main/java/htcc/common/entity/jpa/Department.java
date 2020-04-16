package htcc.common.entity.jpa;

import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Data
@IdClass(Department.Key.class)
@ApiModel(description = "Thông tin phòng ban")
public class Department extends BaseJPAEntity {

    private static final long serialVersionUID = 5922818513825150708L;

    @Id
    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @Id
    @ApiModelProperty(notes = "Mã phòng ban",
                      example = "ZPI")
    public String department = "";

    @Column
    @ApiModelProperty(notes = "Tên phòng ban",
                      example = "Phòng kế toán")
    public String departmentName = "";

    @Column
    @ApiModelProperty(notes = "Tên người đứng đầu (chọn từ danh sách trả về)",
                      example = "admin")
    public String headManager = "";

    @Override
    public String isValid() {
        if (StringUtil.isEmpty(companyId)) {
            return "Mã công ty không được rỗng";
        }

        if (StringUtil.isEmpty(department)) {
            return "Mã chi nhánh không được rỗng";
        }

        if (StringUtil.isEmpty(headManager)) {
            return "Tên người đứng đầu không được rỗng";
        }

        return StringUtil.EMPTY;
    }

    @Data
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Key implements Serializable {

        public String companyId;
        public String department;

        public Key(String key){
            this.companyId = key.split("_")[0];
            this.department = key.split("_")[1];
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
                    department.equalsIgnoreCase(that.department));
        }

        @Override
        public int hashCode() {
            return Objects.hash(companyId, department);
        }
    }
}
