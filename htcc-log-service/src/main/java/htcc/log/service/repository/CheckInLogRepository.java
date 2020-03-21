package htcc.log.service.repository;

import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.CheckOutLogEntity;

public interface CheckInLogRepository {

    CheckInLogEntity getCheckInLog(String companyId, String username, String ymd);

    CheckOutLogEntity getCheckOutLog(String companyId, String username, String ymd);
}
