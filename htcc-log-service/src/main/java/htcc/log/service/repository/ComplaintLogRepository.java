package htcc.log.service.repository;

import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.complaint.ComplaintLogEntity;

import java.util.List;

public interface ComplaintLogRepository {

    List<ComplaintLogEntity> getComplaintLog(String companyId, String username, String yyyyMM);

    List<ComplaintLogEntity> getComplaintLogByReceiverType(int receiverType, String yyyyMM);

    void updateComplaintLogStatus(UpdateComplaintStatusModel model);

    void resubmitComplaint(String yyyyMM, String complaintId, String newContent);

    ComplaintLogEntity getComplaint(UpdateComplaintStatusModel model);

    void increasePendingComplaintCounter(UpdateComplaintStatusModel model);
}
