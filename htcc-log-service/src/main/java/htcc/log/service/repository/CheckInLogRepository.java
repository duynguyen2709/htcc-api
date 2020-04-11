package htcc.log.service.repository;

import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;

public interface CheckInLogRepository {

    CheckInLogEntity getCheckInLog(String companyId, String username, String ymd);

    CheckOutLogEntity getCheckOutLog(String companyId, String username, String ymd);
}
