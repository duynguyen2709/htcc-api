package htcc.employee.service.repository.impl;

import htcc.common.constant.Constant;
import htcc.common.entity.jpa.Department;
import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.common.entity.role.EmployeePermission;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.PermissionRepository;
import htcc.employee.service.service.jpa.DepartmentService;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.EmployeePermissionService;
import htcc.employee.service.service.jpa.OfficeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@Log4j2
public class PermissionRepositoryImpl implements PermissionRepository {

    @Autowired
    private OfficeService officeService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Autowired
    private EmployeePermissionService employeePermissionService;

    @Override
    public boolean canManageOffice(String companyId, String actor, String officeId) {
        EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, actor));
        if (permission == null) {
            log.error("\n[canManageOffice] employeePermissionService.findById [{}-{}] return null", companyId, actor);
            return false;
        }
        if (StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN)) {
            return true;
        }
        return getCanManageOffices(companyId, actor).contains(officeId);
    }

    @Override
    public boolean canManageEmployee(String companyId, String actor, String targetEmployee) {
        if (actor.equals(targetEmployee)) {
            return true;
        }
        EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, actor));
        if (permission == null) {
            log.error("\n[canManageEmployee] employeePermissionService.findById [{}-{}] return null", companyId, actor);
            return false;
        }
        if (StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN)) {
            return true;
        }
        List<EmployeeInfo> employeeList = getCanManageEmployees(companyId, actor);
        return employeeList.stream().anyMatch(c -> c.getUsername().equals(targetEmployee));
    }

    @Override
    public boolean isSuperAdmin(String companyId, String username) {
        EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, username));
        if (permission == null) {
            log.error("\n[isSuperAdmin] employeePermissionService.findById [{}-{}] return null", companyId, username);
            return false;
        }
        return StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN);
    }

    @Override
    public List<String> getCanManageOffices(String companyId, String username) {
        EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, username));
        if (permission == null) {
            log.error("\n[getCanManageOffices] employeePermissionService.findById [{}-{}] return null", companyId, username);
            return new ArrayList<>();
        }

        if (StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN)) {
            return officeService.findByCompanyId(companyId)
                    .stream()
                    .map(Office::getOfficeId)
                    .collect(Collectors.toList());
        }

        Set<String> officeList = new HashSet<>(permission.getCanManageOffices());
        for (String subordinates : permission.getSubordinates()) {
            officeList.addAll(getCanManageOffices(companyId, subordinates));
        }
        return new ArrayList<>(officeList);
    }

    @Override
    public List<String> getCanManageDepartments(String companyId, String username) {
        EmployeePermission permission = employeePermissionService.findById(new EmployeePermission.Key(companyId, username));
        if (permission == null) {
            log.error("\n[getCanManageDepartments] employeePermissionService.findById [{}-{}] return null", companyId, username);
            return new ArrayList<>();
        }

        if (StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN)) {
            return departmentService.findByCompanyId(companyId)
                    .stream()
                    .map(Department::getDepartment)
                    .collect(Collectors.toList());
        }

        Set<String> departmentList = new HashSet<>(permission.getCanManageDepartments());
        for (String subordinates : permission.getSubordinates()) {
            departmentList.addAll(getCanManageDepartments(companyId, subordinates));
        }
        return new ArrayList<>(departmentList);
    }

    @Override
    public List<EmployeeInfo> getCanManageEmployees(String companyId, String username) {
        EmployeePermission permission = employeePermissionService.findById(
                new EmployeePermission.Key(companyId, username));
        if (permission == null) {
            log.error("\n[getCanManageEmployees] employeePermissionService.findById [{}-{}] return null",
                    companyId, username);
            return new ArrayList<>();
        }

        if (StringUtil.valueOf(permission.getManagerRole()).equalsIgnoreCase(Constant.ROLE_SUPER_ADMIN)) {
            return employeeInfoService.findByCompanyId(companyId);
        }

        List<EmployeeInfo> employeeInfoList = new ArrayList<>();
        for (String subordinates : permission.getSubordinates()) {
            EmployeeInfo employeeInfo = employeeInfoService.findById(new EmployeeInfo.Key(companyId, subordinates));
            if (employeeInfo != null) {
                employeeInfoList.add(employeeInfo);
            }
            else {
                log.error("\n[getCanManageEmployees] employeeInfoService.findById [{}-{}] return null",
                        companyId, subordinates);
            }
            employeeInfoList.addAll(getCanManageEmployees(companyId, subordinates));
        }
        return new ArrayList<>(employeeInfoList);
    }
}
