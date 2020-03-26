package htcc.common.entity.home;

import com.fasterxml.jackson.annotation.JsonIgnore;
import htcc.common.util.DateTimeUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Data
@ApiModel(description = "Response API Home")
public class HomeResponse implements Serializable {

    private static final long serialVersionUID = 5912346685015150708L;

    @ApiModelProperty(notes = "Số lượng khiếu nại chưa xử lý (để hiển thị ở sidebar)",
                      example = "10")
    public int pendingComplaint = 0;

}

