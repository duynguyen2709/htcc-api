package htcc.log.service.repository;

import htcc.common.entity.payslip.ManagerLockSalaryRequest;
import htcc.common.entity.payslip.SalaryLogEntity;

import java.util.List;

public interface SalaryLogRepository {

    List<SalaryLogEntity> getEmployeePayslip(String companyId, String username, String yyyyMM);

    List<SalaryLogEntity> getSalaryLogForManager(String companyId, String yyyyMM);

    List<SalaryLogEntity> getSalaryLogInList(List<String> paySlipIdList, String yyyyMM) throws Exception;

    int deleteSalaryLog(String yyyyMM, String paySlipId);

    void lockSalaryLog(ManagerLockSalaryRequest request) throws Exception;
}
