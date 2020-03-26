package htcc.log.service.repository;

import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.log.ComplaintLogEntity;

import java.util.List;

public interface ComplaintLogRepository {

    List<ComplaintLogEntity> getComplaintLog(String companyId, String username, String yyyyMM);

    List<ComplaintLogEntity> getComplaintLogByReceiverType(int receiverType, String yyyyMM);

    void updateComplaintLogStatus(UpdateComplaintStatusModel model);

    ComplaintLogEntity getComplaint(UpdateComplaintStatusModel model);
}
