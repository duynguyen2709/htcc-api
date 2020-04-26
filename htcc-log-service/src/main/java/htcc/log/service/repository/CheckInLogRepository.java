package htcc.log.service.repository;

import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;

import java.util.List;

public interface CheckInLogRepository {

    List<CheckInLogEntity> getCheckInLog(String companyId, String username, String ymd);

    List<CheckOutLogEntity> getCheckOutLog(String companyId, String username, String ymd);
}
