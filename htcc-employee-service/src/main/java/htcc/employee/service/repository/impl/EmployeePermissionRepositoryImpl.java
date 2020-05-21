package htcc.employee.service.repository.impl;

import htcc.common.entity.jpa.EmployeeInfo;
import htcc.common.entity.jpa.Office;
import htcc.employee.service.repository.EmployeePermissionRepository;
import htcc.employee.service.service.jpa.EmployeeInfoService;
import htcc.employee.service.service.jpa.OfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class EmployeePermissionRepositoryImpl implements EmployeePermissionRepository {

    // TODO : Implement this class

    @Autowired
    private OfficeService officeService;

    @Autowired
    private EmployeeInfoService employeeInfoService;

    @Override
    public boolean canManageOffice(String companyId, String actor, String officeId) {
        return true;
    }

    @Override
    public boolean canManageEmployee(String companyId, String actor, String targetEmployee) {
        return true;
    }

    @Override
    public boolean isSuperAdmin(String companyId, String username) {
        return true;
    }

    @Override
    public List<String> getCanManageOffices(String companyId, String username) {
        return officeService.findByCompanyId(companyId)
                .stream()
                .map(Office::getOfficeId)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmployeeInfo> getCanManageEmployees(String companyId, String username) {
        return employeeInfoService.findByCompanyId(companyId);
    }
}
