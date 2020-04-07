package htcc.log.service.repository;

import htcc.common.entity.log.LeavingRequestLogEntity;

import java.util.List;

public interface LeavingRequestLogRepository {

    List<LeavingRequestLogEntity> getLeavingRequestLog(String companyId, String username, String year);
}
