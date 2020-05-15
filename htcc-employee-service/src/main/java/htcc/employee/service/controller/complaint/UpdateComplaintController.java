package htcc.employee.service.controller.complaint;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.complaint.ComplaintResponse;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.ComplaintService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "API của quản lý",
     description = "API để xử lý phản hồi/ khiếu nại cho quản lý")
@RestController
@Log4j2
public class UpdateComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @Autowired
    private EmployeeInfoService employeeInfoService;


    @ApiOperation(value = "Lấy danh sách khiếu nại", response = ComplaintResponse.class)
    @GetMapping("/complaint/{companyId}/{month}")
    public BaseResponse getListComplaintByCompany(@ApiParam(value = "[Path] Mã công ty", required = true)
                                         @PathVariable String companyId,
                                         @ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                         @PathVariable String month) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            List<ComplaintResponse> list = complaintService.getListComplaintLogByCompany(companyId, yyyyMM);
            formatSenderName(list);

            response.data = list;

        } catch (Exception e) {
            log.error(String.format("getListComplaintByCompany [%s-%s] ex", companyId,month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

    private void formatSenderName(List<ComplaintResponse> list) {
        Map<String, String> fullNameMap = new HashMap<>();

        for (ComplaintResponse entity : list) {
            if (entity.getIsAnonymous() == 0) {
                try {
                    String key = entity.getCompanyId() + "_" + entity.getUsername();
                    if (fullNameMap.containsKey(key)) {
                        String fullName = fullNameMap.get(key).isEmpty() ? "" : String.format(" - %s", fullNameMap.get(key));
                        entity.sender = String.format("%s%s", entity.getUsername(), fullName);
                    }
                    else {
                        EmployeeInfo employeeInfo = employeeInfoService.findById(
                                new EmployeeInfo.Key(entity.getCompanyId(), entity.getUsername()));
                        if (employeeInfo != null) {
                            fullNameMap.put(key, employeeInfo.getFullName());
                        }
                        else {
                            fullNameMap.put(key, "");
                        }
                        String fullName = fullNameMap.get(key).isEmpty() ? "" : String.format(" - %s", fullNameMap.get(key));
                        entity.sender = String.format("%s%s", entity.getUsername(), fullName);
                    }
                } catch (Exception e) {
                    log.error("[formatSenderName] [{}]", StringUtil.toJsonString(entity), e);
                }
            }
        }
    }

    @ApiOperation(value = "Cập nhật trạng thái khiếu nại", response = BaseResponse.class)
    @PutMapping("/complaint/status")
    public BaseResponse updateComplaintStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                  @RequestBody UpdateComplaintStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            if (request.getStatus() != ComplaintStatusEnum.DONE.getValue() &&
                    request.getStatus() != ComplaintStatusEnum.REJECTED.getValue()) {
                return new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID, String.format("Trạng thái %s không hợp lệ", request.getStatus()));
            }

            if (!DateTimeUtil.isRightFormat(request.getYyyyMM(), "yyyyMM")) {
                return new BaseResponse(ReturnCodeEnum.DATE_WRONG_FORMAT, String.format("Tháng %s không phù hợp định dạng yyyyMM", request.getYyyyMM()));
            }

            response = complaintService.updateComplaintStatus(request);
        } catch (Exception e) {
            log.error(String.format("updateComplaintStatus [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
