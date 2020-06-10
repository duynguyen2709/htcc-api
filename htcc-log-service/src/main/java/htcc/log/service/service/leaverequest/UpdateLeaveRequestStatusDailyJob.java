package htcc.log.service.service.leaverequest;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.leavingrequest.LeavingRequest;
import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.leavingrequest.LeavingRequestModel;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.repository.LeavingRequestLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Log4j2
public class UpdateLeaveRequestStatusDailyJob {

    @Autowired
    private LeavingRequestLogRepository repo;

    @Scheduled(cron = "${service.schedule.leaveRequest}")
    public void autoUpdateStatusJob(){

        log.info("### UpdateLeaveRequestStatusDailyJob triggered ###");

        long now = System.currentTimeMillis();
        String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");

        try {
            List<LeavingRequestLogEntity> logList = repo.getListPendingLeavingRequestLog(yyyyMMdd.substring(0, 6));
            if (logList == null) {
                throw new Exception("repo.getListPendingLeavingRequestLog return null");
            }

            List<LeavingRequestModel> modelList = logList
                    .stream()
                    .map(LeavingRequestModel::new)
                    .filter(new OverdueLeavingRequestFilter(yyyyMMdd))
                    .collect(Collectors.toList());

            for (LeavingRequestModel model : modelList) {
                try {
                    UpdateLeavingRequestStatusModel updModel = new UpdateLeavingRequestStatusModel();
                    updModel.setLeavingRequestId(model.getLeavingRequestId());
                    updModel.setYyyyMM(DateTimeUtil.parseTimestampToString(model.getClientTime(), "yyyyMM"));
                    updModel.setStatus(ComplaintStatusEnum.REJECTED.getValue());
                    updModel.setApprover("Hệ thống");
                    updModel.setResponse("Đơn nghỉ phép quá hạn phê duyệt");

                    int response = repo.updateLeavingRequestLogStatus(updModel);
                    if (response != 1) {
                        throw new Exception("repo.updateLeavingRequestLogStatus return " + response);
                    }
                } catch (Exception e) {
                    log.error("[updateLeavingRequestLogStatus] {} ex", StringUtil.toJsonString(model), e);
                }
            }

        } catch (Exception e) {
            log.error("[autoUpdateStatusJob] ex", e);
        }

        log.info("### End UpdateLeaveRequestStatusDailyJob ###");
    }

    private static class OverdueLeavingRequestFilter implements Predicate<LeavingRequestModel> {

        private String yyyyMMdd;

        OverdueLeavingRequestFilter(String yyyyMMdd) {
            this.yyyyMMdd = yyyyMMdd;
        }

        @Override
        public boolean test(LeavingRequestModel leavingRequestModel) {
            leavingRequestModel.getDetail().sort(LeavingRequest.LeavingDayDetail.getComparator());
            String lastYYYYMMDD = leavingRequestModel.getDetail().get(leavingRequestModel.getDetail().size() - 1).getDate();
            return Long.parseLong(lastYYYYMMDD) < Long.parseLong(this.yyyyMMdd);
        }
    }
}
