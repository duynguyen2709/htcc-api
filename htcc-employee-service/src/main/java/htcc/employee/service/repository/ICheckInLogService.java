package htcc.employee.service.repository;

import htcc.employee.service.entity.checkin.CheckinModel;
import org.springframework.stereotype.Repository;

public interface ICheckInLogService {

    CheckinModel getCheckInLog(String companyId, String username, String yyyyMMdd);

    CheckinModel getCheckOutLog(String companyId, String username, String yyyyMMdd);
}
