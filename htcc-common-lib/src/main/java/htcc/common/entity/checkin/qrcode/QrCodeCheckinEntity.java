package htcc.common.entity.checkin.qrcode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@ApiModel(description = "Object để điểm danh = QR Code")
public class QrCodeCheckinEntity {

    private static final long serialVersionUID = 59264685830011708L;

    @ApiModelProperty(notes = "ID của QR",
                      example = "VNG-CAMPUS-123")
    @NotEmpty
    public String qrCodeId;

    @ApiModelProperty(notes = "Mã công ty",
                      example = "VNG")
    @NotEmpty
    public String companyId;

    @ApiModelProperty(notes = "Mã chi nhánh",
                      example = "CAMPUS")
    @NotEmpty
    public String officeId;

    @ApiModelProperty(notes = "Thời gian QR được tạo")
    public long genTime = 0L;
}
