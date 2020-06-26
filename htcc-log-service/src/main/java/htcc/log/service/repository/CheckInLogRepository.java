package htcc.log.service.repository;

import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.UpdateCheckInStatusModel;

import java.util.List;

public interface CheckInLogRepository {

    List<CheckInLogEntity> getCheckInLog(String companyId, String username, String ymd);

    List<CheckinModel> getPendingLog(String companyId, String ymd);

    List<CheckOutLogEntity> getCheckOutLog(String companyId, String username, String ymd);

    int updateOppositeId(CheckinModel checkOutModel);

    CheckinModel getOneCheckInLog(UpdateCheckInStatusModel request);

    int updateCheckInStatus(UpdateCheckInStatusModel request, String companyId) throws Exception;

    void increasePendingCheckInCounter(CheckinModel model);
}
