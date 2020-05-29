package htcc.employee.service.controller.contact;

import htcc.common.comparator.EmployeeIdComparator;
import htcc.common.constant.ReturnCodeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.contact.ContactFilterEntity;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.util.StringUtil;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.DepartmentService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.OfficeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Api(tags = "API danh bạ công ty",
     description = "Lấy thông tin danh bạ của nhân viên công ty")
@RestController
@Log4j2
public class ContactController {

    @Autowired
    private EmployeeInfoService employeeService;

    @Autowired
    private OfficeService officeService;

    @Autowired
    private DepartmentService departmentService;

    @ApiOperation(value = "Lấy danh bạ công ty", response = EmployeeInfo.class)
    @GetMapping("/contacts/{companyId}")
    public BaseResponse getListContact(@ApiParam(name = "companyId", value = "[Path] Mã công ty", defaultValue = "VNG", required = true)
                                           @PathVariable String companyId,
                                       @ApiParam(name = "officeId", value = "[QueryString] Mã chi nhánh", defaultValue = "CAMPUS", required = false)
                                       @RequestParam(required = false) String officeId,
                                       @ApiParam(name = "department", value = "[QueryString] Mã phòng ban", defaultValue = "PMA", required = false)
                                       @RequestParam(required = false) String department,
                                       @ApiParam(name = "search", value = "[QueryString] Search theo mã nhân viên/ họ tên", required = false)
                                       @RequestParam(required = false) String search,
                                       @ApiParam(name = "index", value = "[QueryString] Phân trang (0,1,2...)", defaultValue = "0", required = false)
                                       @RequestParam(required = false, defaultValue = "0") Integer index,
                                       @ApiParam(name = "size", value = "[QueryString] Số record mỗi trang (0 = lấy toàn bộ), nếu khác 0 cần gửi thêm index để lấy trang tiếp theo",
                                                 defaultValue = "0", required = false)
                                       @RequestParam(required = false, defaultValue = "0") Integer size) {
        BaseResponse<List<EmployeeInfo>> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            List<EmployeeInfo> listEmployee = new ArrayList<>();

            if (StringUtil.isEmpty(search)) {
                listEmployee = employeeService.findByCompanyId(companyId);
            } else {
                listEmployee = employeeService.findByFullTextSearch(search)
                        .stream().filter(e -> e.getCompanyId().equals(companyId))
                        .collect(Collectors.toList());
            }

            if (!StringUtil.isEmpty(officeId)) {
                // filter by office
                listEmployee = listEmployee.stream()
                        .filter(e -> e.getOfficeId().equals(officeId.trim()))
                        .collect(Collectors.toList());
            }

            if (!StringUtil.isEmpty(department)) {
                // filter by department
                listEmployee = listEmployee.stream()
                        .filter(e -> e.getDepartment().equals(department.trim()))
                        .collect(Collectors.toList());
            }

            // sort by Id
            listEmployee.sort(new EmployeeIdComparator());

            // paging
            if (size > 0) {
                int startIndex = size * index;
                if (startIndex >= listEmployee.size()) {
                    // reach end of list
                    listEmployee = new ArrayList<>();
                } else {
                    // normal case
                    int endIndex = size * (index + 1);
                    if (endIndex > listEmployee.size()) {
                        endIndex = listEmployee.size();
                    }
                    listEmployee = listEmployee.subList(startIndex, endIndex);
                }
            }

            response.setData(listEmployee);
        } catch (Exception e){
            log.error(String.format("[getListContact] [%s-%s-%s-%s]",
                    companyId, StringUtil.valueOf(officeId), StringUtil.valueOf(department), size), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }



    @ApiOperation(value = "Lấy list value để filter theo phòng ban (departmentList) & chi nhánh (officeIdList)",
                  response = ContactFilterEntity.class)
    @GetMapping("/contacts/filter/{companyId}")
    public BaseResponse getContactFilter(@ApiParam(name = "companyId", value = "[Path] Mã công ty",
                                                   defaultValue = "VNG", required = true)
                                             @PathVariable String companyId) {
        BaseResponse<ContactFilterEntity> response = new BaseResponse<>(ReturnCodeEnum.SUCCESS);
        try {
            Set<String> departments = departmentService.findByCompanyId(companyId)
                    .stream()
                    .map(Department::getDepartment)
                    .collect(Collectors.toSet());

            Set<String> officeId = officeService.findByCompanyId(companyId)
                    .stream()
                    .map(Office::getOfficeId)
                    .collect(Collectors.toSet());

            List<String> departmentList = new ArrayList<>(departments);
            List<String> officeIdList = new ArrayList<>(officeId);

            ContactFilterEntity data = new ContactFilterEntity(departmentList, officeIdList);

            response.setData(data);
        } catch (Exception e){
            log.error(String.format("[getContactFilter] [%s]", companyId), e);
            response = new BaseResponse<>(e);
        }
        return response;
    }
}
