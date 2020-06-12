package htcc.employee.service.service.statistic;

import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.employee.service.service.checkin.CheckInService;
import htcc.employee.service.service.leavingrequest.LeavingRequestService;
import htcc.employee.service.service.shiftarrangement.ShiftArrangementService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class EmployeeStatisticService {

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private ShiftArrangementService shiftArrangementService;

    @Autowired
    private LeavingRequestService leavingRequestService;

    @Async("asyncExecutor")
    public CompletableFuture<List<CheckinModel>> getListCheckInTime(String companyId, String username, String yyyyMMdd) {
        List<CheckinModel> result = new ArrayList<>();

        try {
            CompletableFuture<List<CheckinModel>> checkInLog = checkInService.getCheckInLog(companyId, username, yyyyMMdd);
            CompletableFuture<List<CheckinModel>> checkOutLog = checkInService.getCheckOutLog(companyId, username, yyyyMMdd);
            CompletableFuture.allOf(checkInLog, checkOutLog).join();

            result.addAll(checkInLog.get());
            result.addAll(checkOutLog.get());
        } catch (Exception e) {
            log.error("[getListCheckInTime] [{}-{}-{}] ex", companyId, username, yyyyMMdd, e);
        }
        return CompletableFuture.completedFuture(result);
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<ShiftArrangementModel>> getListShiftArrangement(String companyId, String username, String yyyyMMdd) {
        List<ShiftArrangementModel> result = new ArrayList<>();

        try {
            result.addAll(shiftArrangementService.getShiftArrangementListByEmployee(companyId, username, yyyyMMdd));
        } catch (Exception e) {
            log.error("[getListShiftArrangement] [{}-{}-{}] ex", companyId, username, yyyyMMdd, e);
        }
        return CompletableFuture.completedFuture(result);
    }

    @Async("asyncExecutor")
    public CompletableFuture<List<LeavingRequestModel>> getListLeavingRequest(String companyId, String username, String yyyyMMdd) {
        List<LeavingRequestModel> result = new ArrayList<>();

        try {
            result.addAll(leavingRequestService.getListLeavingRequestByDate(companyId, username, yyyyMMdd));
        } catch (Exception e) {
            log.error("[getListLeavingRequest] [{}-{}-{}] ex", companyId, username, yyyyMMdd, e);
        }
        return CompletableFuture.completedFuture(result);
    }
}
