package htcc.employee.service.controller.company;

import htcc.common.constant.AccountStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.jpa.CompanyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(tags = "API thông tin công ty (CỦA QUẢN LÝ)")
@RestController
@Log4j2
public class CompanyRestController {

    @Autowired
    private CompanyService companyService;

    @ApiOperation(value = "Lấy thông tin công ty", response = Company.class)
    @GetMapping("/companies/{companyId}")
    public BaseResponse getCompanyInfo(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                      @PathVariable String companyId){
        BaseResponse response = new BaseResponse(ReturnCodeEnum.SUCCESS);
        try {
            response.setData(companyService.findById(companyId));
        } catch (Exception e){
            log.error("[getCompanyInfo] [{}] ex", companyId, e);
        }
        return response;
    }





    @ApiOperation(value = "Cập nhật thông tin công ty", response = Company.class)
    @PutMapping("/companies/{companyId}")
    public BaseResponse updateCompanyInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                                         @PathVariable("companyId") String companyId,
                                         @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                         @RequestBody Company company){
        BaseResponse<Company> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thông tin công ty thành công");
        try {
            company.setStatus(AccountStatusEnum.ACTIVE.getValue());
            String error = company.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Company oldCompany = companyService.findById(companyId);
            if (oldCompany == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy công ty [%s]", companyId));
                return response;
            }

            company.setCompanyId(companyId);
            company.setSupportedScreens(oldCompany.getSupportedScreens());
            company = companyService.update(company);
            response.setData(company);
        } catch (Exception e) {
            log.error("[updateCompanyInfo] {} ex", StringUtil.toJsonString(company), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }
}
