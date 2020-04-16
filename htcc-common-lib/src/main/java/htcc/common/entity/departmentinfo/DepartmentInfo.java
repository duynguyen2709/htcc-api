package htcc.common.entity.departmentinfo;

import htcc.common.entity.jpa.BaseJPAEntity;
import htcc.common.entity.jpa.Department;
import htcc.common.util.StringUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "Thông tin phòng ban")
public class DepartmentInfo implements Serializable {

    private static final long serialVersionUID = 112627098300550708L;

    @ApiModelProperty(notes = "Danh sách phòng ban")
    public List<Department> departmentList = new ArrayList<>();

    @ApiModelProperty(notes = "Danh sách người đứng đầu có thể chọn")
    public List<String> headManagerList = new ArrayList<>();
}
