package htcc.admin.service.controller;

import htcc.admin.service.service.rest.EmployeeCompanyService;
import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;

@Api(tags = "Company REST APIs",
     description = "API quản lý danh sách công ty")
@RestController
@Log4j2
public class CompanyController {

    @Autowired
    private EmployeeCompanyService companyService;

    @ApiOperation(value = "Lấy danh sách công ty", response = Company.class)
    @GetMapping("/companies")
    public BaseResponse getListCompany(){
        return companyService.getListCompany();
    }




    @ApiOperation(value = "Tạo công ty mới", response = Company.class)
    @PostMapping("/companies")
    public BaseResponse createCompany(@ApiParam(value = "[Body] Thông tin công ty mới", required = true)
                                   @RequestBody Company company) {
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            String error = company.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            return companyService.createCompany(company);
        } catch (Exception e) {
            log.error("[createCompany] {} ex", StringUtil.toJsonString(company), e);
            return new BaseResponse(e);
        }
    }




    @ApiOperation(value = "Cập nhật thông tin của công ty", response = Company.class)
    @PutMapping("/companies/{companyId}")
    public BaseResponse updateCompanyInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                               @PathVariable("companyId") String companyId,
                               @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                          @RequestBody Company company){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            String error = company.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            company.setCompanyId(companyId);
            return companyService.updateCompanyInfo(company);
        } catch (Exception e) {
            log.error("[updateCompanyInfo] {} ex", StringUtil.toJsonString(company), e);
            return new BaseResponse(e);
        }
    }




    @ApiOperation(value = "Khóa/Mở khóa công ty", response = Company.class)
    @PutMapping("/companies/{companyId}/status/{newStatus}")
    public BaseResponse lockCompany(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                    @PathVariable("companyId") String companyId,
                                    @ApiParam(value = "[Path] Trạng thái mới cần update", required = true, defaultValue = "0")
                                    @PathVariable int newStatus) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (AccountStatusEnum.fromInt(newStatus) == null) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(String.format("Trạng thái mới [%s] không hợp lệ", newStatus));
                return response;
            }

            Company company = new Company();
            company.setCompanyId(companyId);
            company.setStatus(newStatus);

            return companyService.updateCompanyStatus(company);
        } catch (Exception e){
            log.error("[lockCompany] [{} - {}] ex", companyId, newStatus, e);
            return new BaseResponse<>(e);
        }
    }
}
