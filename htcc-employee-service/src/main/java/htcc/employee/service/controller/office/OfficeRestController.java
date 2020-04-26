package htcc.employee.service.controller.office;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Office;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "API quản lý danh sách chi nhánh (CỦA QUẢN LÝ)")
@RestController
@Log4j2
public class OfficeRestController {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private EmployeeInfoService employeeService;

    @ApiOperation(value = "Lấy danh sách chi nhánh", response = Office.class)
    @GetMapping("/offices/{companyId}")
    public BaseResponse getListOffice(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                          @PathVariable String companyId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            response.setData(officeService.findByCompanyId(companyId));
        } catch (Exception e){
            log.error("[getListOffice] [{}] ex", companyId, e);
            response = new BaseResponse(e);
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

            error = checkAlreadyHasHeadquarter(office);
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Office oldOffice = officeService.findById(new Office.Key(office.getCompanyId(), office.getOfficeId()));
            if (oldOffice != null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_ALREADY_EXISTED);
                response.setReturnMessage(String.format("Chi nhánh [%s] đã tồn tại. Vui lòng nhập lại", office.getOfficeId()));
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

    private String checkAlreadyHasHeadquarter(Office office) {
        if (!office.getIsHeadquarter()) {
            return StringUtil.EMPTY;
        }

        List<Office> officeList = DbStaticConfigMap.findOfficeByCompanyId(office.getCompanyId());

        for (Office o : officeList) {
            if (o.getIsHeadquarter() && !o.getOfficeId().equals(office.getOfficeId())){
                return String.format("Công ty đã có trụ sở chính %s. Vui lòng cập nhật lại.", o.getOfficeId());
            }
        }

        return StringUtil.EMPTY;
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

            error = checkAlreadyHasHeadquarter(office);
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
        } finally {
            employeeService.deleteOffice(companyId, officeId);
        }
        return response;
    }
}
