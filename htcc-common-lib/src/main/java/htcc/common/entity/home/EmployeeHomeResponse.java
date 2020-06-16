package htcc.common.entity.home;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(description = "Response API Home của nhân viên")
public class EmployeeHomeResponse implements Serializable {

    private static final long serialVersionUID = 5912345687015150708L;

    @ApiModelProperty(notes = "Số lượng notification chưa đọc",
                      example = "10")
    public int unreadNotifications = 0;

    public List<Integer> displayScreens = new ArrayList<>();
}
