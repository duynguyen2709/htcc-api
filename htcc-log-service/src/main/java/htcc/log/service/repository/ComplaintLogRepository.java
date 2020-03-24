package htcc.log.service.repository;

import htcc.common.entity.log.ComplaintLogEntity;

import java.util.List;

public interface ComplaintLogRepository {

    List<ComplaintLogEntity> getComplaintLog(String companyId, String username, String yyyyMM);
}
