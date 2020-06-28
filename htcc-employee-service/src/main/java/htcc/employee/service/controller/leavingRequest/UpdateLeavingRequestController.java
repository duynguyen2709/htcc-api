package htcc.employee.service.controller.leavingRequest;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.leavingrequest.LeavingRequestResponse;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.leavingrequest.LeavingRequestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "API xử lý đơn xin nghỉ phép (CỦA QUẢN LÝ)",
     description = "API để phê duyệt đơn xin nghỉ phép")
@RestController
@Log4j2
public class UpdateLeavingRequestController {

    @Autowired
    private LeavingRequestService service;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EmployeePermissionRepository permissionRepository;

    @ApiOperation(value = "Lấy danh sách đơn xin nghỉ phép", response = LeavingRequestResponse.class)
    @GetMapping("/leaving/{companyId}/{month}")
    public BaseResponse getListLeavingRequest(@ApiParam(value = "[Path] Mã công ty", required = true)
                                                  @PathVariable String companyId,
                                              @ApiParam(value = "[Path] Tháng (yyyyMM)", required = true)
                                              @PathVariable String month,
                                              @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String username) {
        BaseResponse<List<LeavingRequestResponse>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String yyyyMM = StringUtil.valueOf(month);
            if (!DateTimeUtil.isRightFormat(yyyyMM, "yyyyMM")) {
                return new BaseResponse<>(ReturnCodeEnum.DATE_WRONG_FORMAT,
                        String.format("Tháng %s không phù hợp định dạng yyyyMM", month));
            }

            List<LeavingRequestResponse> list = service.getLeavingRequestLogByCompanyId(companyId, yyyyMM);

            List<LeavingRequestResponse> dataResponse = new ArrayList<>();

            Map<String, EmployeeInfo> employeeCacheMap = new HashMap<>();
            for (LeavingRequestResponse entity : list) {
                if (permissionRepository.canManageEmployee(companyId, username, entity.getSender())) {
                    if (!employeeCacheMap.containsKey(entity.getSender())) {
                        EmployeeInfo employee = employeeInfoService.findById(new EmployeeInfo.Key(companyId, entity.getSender()));
                        if (employee != null) {
                            employeeCacheMap.put(employee.getUsername(), employee);
                        }
                    }

                    EmployeeInfo employeeInfo = employeeCacheMap.get(entity.getSender());
                    if (employeeInfo != null) {
                        String sender = String.format("%s (%s)", employeeInfo.getFullName(), employeeInfo.getUsername());
                        entity.setSender(sender);
                    }

                    dataResponse.add(entity);
                }
            }

            response.setData(dataResponse);

        } catch (Exception e) {
            log.error(String.format("getListLeavingRequest [%s-%s] ex", companyId,month), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Cập nhật trạng thái khiếu nại", response = BaseResponse.class)
    @PutMapping("/leaving/status")
    public BaseResponse updateLeavingRequestStatus(@ApiParam(value = "[Body] Trạng thái mới cần update", required = true)
                                                  @RequestBody UpdateLeavingRequestStatusModel request) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            String error = request.isValid();
            if (!StringUtil.isEmpty(error)) {
                response = new BaseResponse(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            response = service.updateLeavingRequestStatus(request);
        } catch (Exception e) {
            log.error(String.format("updateLeavingRequestStatus [%s] ex", StringUtil.toJsonString(request)), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }

}
