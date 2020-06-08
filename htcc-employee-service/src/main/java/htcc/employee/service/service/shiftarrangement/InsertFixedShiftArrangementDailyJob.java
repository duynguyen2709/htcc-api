package htcc.employee.service.service.shiftarrangement;

import htcc.common.constant.ReturnCodeEnum;
import htcc.common.constant.ShiftArrangementTypeEnum;
import htcc.common.entity.base.BaseResponse;
import htcc.common.entity.shift.FixedShiftArrangement;
import htcc.common.entity.shift.ShiftArrangementModel;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.employee.service.service.LogService;
import htcc.employee.service.service.jpa.FixedShiftArrangementService;
import htcc.employee.service.service.jpa.ShiftTimeService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@EnableScheduling
@Log4j2
public class InsertFixedShiftArrangementDailyJob {

    @Autowired
    private FixedShiftArrangementService fixedShiftArrangementService;

    @Autowired
    private LogService logService;

    @Autowired
    private ShiftTimeService shiftTimeService;

    @Scheduled(cron = "${service.schedule.fixedShiftArrangement}")
    public void autoInsertLogJob(){

        log.info("### InsertFixedShiftArrangementDailyJob triggered ###");

        long now = System.currentTimeMillis();
        String yyyyMMdd = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");
        int weekDay = DateTimeUtil.getWeekDayInt(yyyyMMdd);

        List<FixedShiftArrangement> all = fixedShiftArrangementService.findAll()
                .stream()
                .filter(c -> c.getWeekDay() == weekDay)
                .collect(Collectors.toList());

        all.forEach(entity -> {
            try {
                ShiftTime shiftTime = shiftTimeService.findById(
                        new ShiftTime.Key(entity.getCompanyId(), entity.getOfficeId(), entity.getShiftId()));

                if (shiftTime == null) {
                    throw new Exception("shiftTimeService.findById return null");
                }

                ShiftArrangementModel model = new ShiftArrangementModel();
                model.setActionTime(now);
                model.setCompanyId(entity.getCompanyId());
                model.setUsername(entity.getUsername());
                model.setOfficeId(entity.getOfficeId());
                model.setShiftData(shiftTime);
                model.setArrangeDate(yyyyMMdd);
                model.setWeek(DateTimeUtil.getWeekNum(yyyyMMdd));
                model.setFixed(true);
                model.setActor(entity.getActor());
                model.setArrangementId(String.format("%s-%s-%s-%s-%s-%s",
                        model.getArrangeDate(), model.getCompanyId(), model.getOfficeId(), shiftTime.getShiftId(),
                        model.getUsername(), ShiftArrangementTypeEnum.FIXED.getValue()));

                BaseResponse response = logService.insertShiftArrangement(model);
                if (response == null || response.getReturnCode() != ReturnCodeEnum.SUCCESS.getValue()){
                    throw new Exception(String.format("logService.insertShiftArrangement request = %s, response = %s",
                            StringUtil.toJsonString(model), StringUtil.toJsonString(response)));
                }

                log.info("[autoInsertLogJob] insert succeed, entity = {}", StringUtil.toJsonString(entity));
            } catch (Exception e){
                log.error("[autoInsertLogJob] {} ex", StringUtil.toJsonString(entity), e);
            }
        });

        log.info("### End InsertFixedShiftArrangementDailyJob ###");
    }
}
