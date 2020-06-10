package htcc.log.service.repository;

import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;

import java.util.List;

public interface LeavingRequestLogRepository {

    List<LeavingRequestLogEntity> getLeavingRequestLog(String companyId, String username, String year);

    List<LeavingRequestLogEntity> getListLeavingRequestLogByCompany(String companyId, String yyyyMM);

    List<LeavingRequestLogEntity> getListPendingLeavingRequestLog(String yyyyMM);

    int updateLeavingRequestLogStatus(UpdateLeavingRequestStatusModel model);

    LeavingRequestLogEntity getOneLeavingRequest(UpdateLeavingRequestStatusModel model);

    void increasePendingLeavingRequestCounter(UpdateLeavingRequestStatusModel model, String companyId);
}
