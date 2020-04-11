package htcc.employee.service.controller.office;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.entity.jpa.Office;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API quản lý danh sách chi nhánh (CỦA QUẢN LÝ)")
@RestController
@Log4j2
public class OfficeRestController {

    @Autowired
    private OfficeService officeService;

    @ApiOperation(value = "Lấy danh sách chi nhánh", response = Office.class)
    @GetMapping("/offices/{companyId}")
    public BaseResponse getListOffice(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                          @PathVariable String companyId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            response.setData(officeService.findByCompanyId(companyId));
        } catch (Exception e){
            log.error("[getListOffice] [{}] ex", companyId, e);
        }
        return response;
    }




    @ApiOperation(value = "Tạo chi nhánh mới", response = Office.class)
    @PostMapping("/offices")
    public BaseResponse createOffice(@ApiParam(value = "[Body] Thông tin chi nhánh mới", required = true)
                                      @RequestBody Office office) {
        BaseResponse<Office> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Tạo chi nhánh mới thành công");
        try {
            String error = office.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            office = officeService.create(office);
            response.setData(office);
        } catch (Exception e) {
            log.error("[createOffice] {} ex", StringUtil.toJsonString(office), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật thông tin của chi nhánh", response = Office.class)
    @PutMapping("/offices/{companyId}/{officeId}")
    public BaseResponse updateOfficeInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                                          @PathVariable("companyId") String companyId,
                                         @ApiParam(value = "[Path] Mã chi nhánh", required = true)
                                         @PathVariable("officeId") String officeId,
                                          @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                          @RequestBody Office office){
        BaseResponse<Office> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thông tin chi nhánh thành công");
        try {
            String error = office.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Office oldOffice = officeService.findById(new Office.Key(companyId, officeId));
            if (oldOffice == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy chi nhánh [%s-%s]", companyId, officeId));
                return response;
            }

            office.setCompanyId(companyId);
            office.setOfficeId(officeId);
            office = officeService.update(office);
            response.setData(office);
        } catch (Exception e) {
            log.error("[updateOfficeInfo] {} ex", StringUtil.toJsonString(office), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Xóa chi nhánh", response = BaseResponse.class)
    @DeleteMapping("/offices/{companyId}/{officeId}")
    public BaseResponse deleteOffice(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                    @PathVariable("companyId") String companyId,
                                    @ApiParam(value = "[Path] Mã chi nhánh", required = true, defaultValue = "CAMPUS")
                                    @PathVariable("officeId") String officeId) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa chi nhánh thành công");
        try {
            Office oldOffice = officeService.findById(new Office.Key(companyId, officeId));
            if (oldOffice == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy chi nhánh [%s-%s]", companyId, officeId));
                return response;
            }

            officeService.delete(new Office.Key(companyId, officeId));
        } catch (Exception e){
            log.error("[deleteOffice] [{} - {}] ex", companyId, officeId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
