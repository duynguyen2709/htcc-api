package htcc.employee.service.repository.impl;

import htcc.employee.service.entity.checkin.CheckinModel;
import htcc.employee.service.repository.ICheckInLogService;
import org.springframework.stereotype.Repository;

@Repository
public class CheckInLogServiceImpl implements ICheckInLogService {

    @Override
    public CheckinModel getCheckInLog(String companyId, String username, String yyyyMMdd) {
        return null;
    }

    @Override
    public CheckinModel getCheckOutLog(String companyId, String username, String yyyyMMdd) {
        return null;
    }
}
