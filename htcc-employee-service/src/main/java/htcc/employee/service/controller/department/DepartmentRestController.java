package htcc.employee.service.controller.department;

import htcc.common.constant.Constant;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.departmentinfo.DepartmentInfo;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.jpa.DepartmentService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.EmployeePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = "API quản lý danh sách phòng ban (CỦA QUẢN LÝ)")
@RestController
@Log4j2
public class DepartmentRestController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeInfoService employeeService;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Autowired
    private PermissionRepository permissionRepository;

    @ApiOperation(value = "Lấy danh sách phòng ban", response = DepartmentInfo.class)
    @GetMapping("/departments/{companyId}")
    public BaseResponse getListDepartment(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                          @PathVariable String companyId,
                                          @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor){
        BaseResponse<DepartmentInfo> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<Department> departmentList = new ArrayList<>();
            List<String> departmentIdList = permissionRepository.getCanManageDepartments(companyId, actor);
            for (String department : departmentIdList) {
                Department dep = departmentService.findById(new Department.Key(companyId, department));
                if (dep != null) {
                    departmentList.add(dep);
                }
            }

            List<String> employeeList = employeeService.findByCompanyId(companyId)
                    .stream()
                    .map(e -> String.format("%s - %s", e.getUsername(), e.getFullName()))
                    .collect(Collectors.toList());

            DepartmentInfo data = new DepartmentInfo(departmentList, employeeList);
            response.setData(data);
        } catch (Exception e){
            log.error("[getListDepartment] [{}] ex", companyId, e);
            response = new BaseResponse<>(e);
        }
        return response;
    }




    @ApiOperation(value = "Tạo phòng ban mới", response = Department.class)
    @PostMapping("/departments")
    public BaseResponse createDepartment(@ApiParam(value = "[Body] Thông tin phòng ban mới", required = true)
                                      @RequestBody Department department,
                                         @ApiParam(hidden = true) @RequestHeader(Constant.USERNAME) String actor) {
        BaseResponse<Department> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Tạo phòng ban mới thành công");
        try {
            String error = department.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Department oldDepartment = departmentService.findById(new Department.Key(department.getCompanyId(), department.getDepartment()));
            if (oldDepartment != null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_ALREADY_EXISTED);
                response.setReturnMessage(String.format("Phòng ban [%s] đã tồn tại. Vui lòng nhập lại", department.getDepartment()));
                return response;
            }

            department = departmentService.create(department);

            if (department != null) {
                EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(department.getCompanyId(), actor));
                List<String> departmentList = permission.getCanManageDepartments();
                departmentList.add(department.getDepartment());
                permission.setCanManageDepartments(departmentList);
                employeePermissionService.update(permission);
            }

            response.setData(department);
        } catch (Exception e) {
            log.error("[createDepartment] {} ex", StringUtil.toJsonString(department), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }


    @ApiOperation(value = "Cập nhật thông tin của phòng ban", response = Department.class)
    @PutMapping("/departments/{companyId}/{department}")
    public BaseResponse updateDepartmentInfo(@ApiParam(value = "[Path] Mã công ty", required = true)
                                          @PathVariable("companyId") String companyId,
                                         @ApiParam(value = "[Path] Mã phòng ban", required = true)
                                         @PathVariable("department") String department,
                                          @ApiParam(value = "[Body] Thông tin mới cần update", required = true)
                                          @RequestBody Department request){
        BaseResponse<Department> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Cập nhật thông tin phòng ban thành công");
        try {
            String error = request.isValid();
            if (!error.isEmpty()) {
                response = new BaseResponse<>(ReturnCodeEnum.PARAM_DATA_INVALID);
                response.setReturnMessage(error);
                return response;
            }

            Department oldDepartment = departmentService.findById(new Department.Key(companyId, department));
            if (oldDepartment == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy phòng ban [%s-%s]", companyId, department));
                return response;
            }

            request.setCompanyId(companyId);
            request.setDepartment(department);
            request = departmentService.update(request);
            response.setData(request);
        } catch (Exception e) {
            log.error("[updateDepartmentInfo] {} ex", StringUtil.toJsonString(request), e);
            response = new BaseResponse<>(e);
        }

        return response;
    }




    @ApiOperation(value = "Xóa phòng ban", response = BaseResponse.class)
    @DeleteMapping("/departments/{companyId}/{department}")
    public BaseResponse deleteDepartment(@ApiParam(value = "[Path] Mã công ty", required = true, defaultValue = "VNG")
                                    @PathVariable("companyId") String companyId,
                                    @ApiParam(value = "[Path] Mã phòng ban", required = true, defaultValue = "CAMPUS")
                                    @PathVariable("department") String department) {
        BaseResponse response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        response.setReturnMessage("Xóa phòng ban thành công");
        try {
            Department oldDepartment = departmentService.findById(new Department.Key(companyId, department));
            if (oldDepartment == null) {
                response = new BaseResponse<>(ReturnCodeEnum.DATA_NOT_FOUND);
                response.setReturnMessage(String.format("Không tìm thấy phòng ban [%s-%s]", companyId, department));
                return response;
            }

            departmentService.delete(new Department.Key(companyId, department));
        } catch (Exception e){
            log.error("[deleteDepartment] [{} - {}] ex", companyId, department, e);
            response = new BaseResponse<>(e);
        } finally {
            employeeService.deleteDepartment(companyId, department);
        }
        return response;
    }
}
