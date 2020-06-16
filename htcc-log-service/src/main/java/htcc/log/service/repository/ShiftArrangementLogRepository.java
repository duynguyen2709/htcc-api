package htcc.log.service.repository;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.shift.ShiftArrangementLogEntity;
import htcc.common.entity.shift.ShiftTime;

import java.util.List;

public interface ShiftArrangementLogRepository {

    List<ShiftArrangementLogEntity> getListShiftArrangementLog(String companyId, int week);

    List<ShiftArrangementLogEntity> getListShiftArrangementLogByEmployee(String companyId, String username, String arrangeDate);

    ShiftArrangementLogEntity getOneShiftArrangementLog(String arrangementId);

    int deleteShiftArrangementLog(String arrangementId);

    int deleteShiftArrangementLog(ShiftTime shiftTime);
}
