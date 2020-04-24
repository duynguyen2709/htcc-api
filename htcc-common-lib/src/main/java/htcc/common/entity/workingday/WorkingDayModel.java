package htcc.common.entity.workingday;

import htcc.common.entity.jpa.WorkingDay;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@ApiModel(description = "Thông tin ngày làm việc trong 1 năm")
public class WorkingDayModel implements Serializable {

    private static final long serialVersionUID = 592281858111515708L;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    public String companyId = "";

    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    public String officeId = "";

    @ApiModelProperty(notes = "Các ngày bình thường (gồm cả ngày nghỉ & làm việc bình thường)")
    public List<WorkingDay> normalDays = new ArrayList<>();

    @ApiModelProperty(notes = "Các ngày đặc biệt (nghỉ lễ, tăng ca...)")
    public List<WorkingDay> specialDays = new ArrayList<>();
}
