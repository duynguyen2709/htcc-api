package htcc.log.service.repository.impl;

import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.ComplaintLogRowMapper;
import htcc.log.service.repository.ComplaintLogRepository;
import htcc.log.service.service.jpa.LogCounterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Log4j2
public class ComplaintLogRepositoryImpl implements ComplaintLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LogCounterService logCounterService;

    @Override
    public List<ComplaintLogEntity> getComplaintLog(String companyId, String username, String yyyyMM) {
        try {
            String tableName = "ComplaintLog" + yyyyMM;

            String query = String.format("SELECT * FROM %s WHERE companyId='%s' " +
                            "AND username='%s'",
                    tableName, companyId, username);

            return jdbcTemplate.query(query, new ComplaintLogRowMapper());

        } catch (Exception e) {
            log.error(String.format("[getComplaintLog] [%s-%s-%s] ex ", companyId, username, yyyyMM), e);
            return null;
        }
    }

    @Override
    public List<ComplaintLogEntity> getComplaintLogByReceiverType(int receiverType, String yyyyMM) {
        try {
            String tableName = "ComplaintLog" + yyyyMM;

            String query = String.format("SELECT * FROM %s WHERE receiverType='%s'",
                    tableName, receiverType);

            return jdbcTemplate.query(query, new ComplaintLogRowMapper());

        } catch (Exception e) {
            log.error(String.format("[getComplaintLogByReceiverType] [%s-%s] ex ", receiverType, yyyyMM), e);
            return null;
        }
    }


    @Override
    @Transactional
    public void updateComplaintLogStatus(UpdateComplaintStatusModel model) {
        String tableName = "ComplaintLog" + model.getYyyyMM();

        String query = String.format("UPDATE %s SET status = ?, response = ? WHERE complaintId='%s'",
                tableName, model.getComplaintId());

        int rowAffected = jdbcTemplate.update(query, model.getStatus(), model.getResponse());
        if (rowAffected == 1) {
            decreaseComplaintLogCounter(model);
        }
    }

    private void decreaseComplaintLogCounter(UpdateComplaintStatusModel model){
        final String logType = "ComplaintLog";

        ComplaintLogEntity entity = getComplaint(model);
        String params = "";
        if (entity.getReceiverType() == 1) {
            params = "1";
        } else {
            params = entity.getReceiverType() + "-" + entity.getCompanyId();
        }

        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(logType, model.yyyyMM, params));

        // new log insert
        if (logCounter == null) {
            List<ComplaintLogEntity> list = getComplaintLogByReceiverType(entity.getReceiverType(), model.yyyyMM);
            int pendingCount = (int) list.stream()
                    .filter(c -> c.getStatus() == ComplaintStatusEnum.PROCESSING.getValue())
                    .count();

            int newCount = 0;
            if (pendingCount > 0){
                newCount = pendingCount -1 ;
            }

            logCounter = new LogCounter(logType, model.yyyyMM, params, newCount);
        } else {
            // update current counter
            int newCount = 0;
            if (logCounter.getCount() > 0) {
                newCount = logCounter.getCount() - 1;
            }

            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }

    @Override
    public void increasePendingComplaintCounter(UpdateComplaintStatusModel model){
        final String logType = "ComplaintLog";

        ComplaintLogEntity entity = getComplaint(model);
        String params = "";
        if (entity.getReceiverType() == 1) {
            params = "1";
        } else {
            params = entity.getReceiverType() + "-" + entity.getCompanyId();
        }

        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(logType, model.yyyyMM, params));

        // new log insert
        if (logCounter == null) {
            int newCount = 1;
            logCounter = new LogCounter(logType, model.yyyyMM, params, newCount);
        } else {
            // update current counter
            int newCount = logCounter.getCount() + 1;
            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }


    @Override
    public ComplaintLogEntity getComplaint(UpdateComplaintStatusModel model) {
        try {
            String tableName = "ComplaintLog" + model.getYyyyMM();

            String query = String.format("SELECT * FROM %s WHERE complaintId='%s'",
                    tableName, model.getComplaintId());

            return jdbcTemplate.queryForObject(query, new ComplaintLogRowMapper());

        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getComplaint] [%s] ex ", StringUtil.toJsonString(model)), e);
        }
        return null;
    }
}
