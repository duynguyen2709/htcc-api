package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.leavingrequest.UpdateLeavingRequestStatusModel;
import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.LeavingRequestLogRowMapper;
import htcc.log.service.repository.LeavingRequestLogRepository;
import htcc.log.service.service.jpa.LogCounterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class LeavingRequestLogRepositoryImpl implements LeavingRequestLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private LogCounterService logCounterService;

    private static final String TABLE_PREFIX = "LeavingRequestLog";

    @Override
    public List<LeavingRequestLogEntity> getLeavingRequestLog(String companyId, String username, String year) {
        List<LeavingRequestLogEntity> result = new ArrayList<>();
        List<String> tableNames = getListTableLeavingRequestLog(year);

        for (String table : tableNames) {
            try {
                final String query = String.format("SELECT * FROM %s WHERE companyId = ? AND username = ?", table);
                result.addAll(jdbcTemplate.query(query, new Object[] {companyId, username},
                        new LeavingRequestLogRowMapper()));
            } catch (Exception e) {
                log.error("[getLeavingRequestLog] [{}-{}-{}]", table, companyId, username, e);
            }
        }
        return result;
    }

    @Override
    public List<LeavingRequestLogEntity> getListLeavingRequestLogByCompany(String companyId, String yyyyMM) {
        try {
            final String table = TABLE_PREFIX + yyyyMM;
            final String query = String.format("SELECT * FROM %s WHERE companyId = ?", table);
            return jdbcTemplate.query(query, new Object[]{companyId},
                    new LeavingRequestLogRowMapper());
        } catch (Exception e) {
            log.error("[getListLeavingRequestLogByCompany] [{}-{}]", companyId, yyyyMM, e);
            return null;
        }
    }

    @Override
    @Transactional
    public int updateLeavingRequestLogStatus(UpdateLeavingRequestStatusModel model) {
        final String tableName = TABLE_PREFIX + model.getYyyyMM();

        final String query = String.format("UPDATE %s SET status = ?, response = ?, approver = ? WHERE leavingRequestId='%s'",
                tableName, model.getLeavingRequestId());

        int rowAffected = jdbcTemplate.update(query, model.getStatus(), model.getResponse(), model.getApprover());
        if (rowAffected == 1) {
            decreaseLeavingRequestCounter(model);
        }

        return rowAffected;
    }

    private void decreaseLeavingRequestCounter(UpdateLeavingRequestStatusModel model) {
        LeavingRequestLogEntity entity = getOneLeavingRequest(model);

        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(TABLE_PREFIX, model.getYyyyMM(), entity.getCompanyId()));

        // new log insert
        if (logCounter == null) {
            List<LeavingRequestLogEntity> list = getListLeavingRequestLogByCompany(entity.getCompanyId(), model.getYyyyMM());
            int pendingCount = (int) list.stream()
                    .filter(c -> c.getStatus() == ComplaintStatusEnum.PROCESSING.getValue())
                    .count();

            int newCount = 0;
            if (pendingCount > 0){
                newCount = pendingCount -1 ;
            }

            logCounter = new LogCounter(TABLE_PREFIX, model.getYyyyMM(), entity.getCompanyId(), newCount);
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
    public LeavingRequestLogEntity getOneLeavingRequest(UpdateLeavingRequestStatusModel model) {
        try {
            final String tableName = TABLE_PREFIX + model.getYyyyMM();

            String query = String.format("SELECT * FROM %s WHERE leavingRequestId = '%s'",
                    tableName, model.getLeavingRequestId());

            return jdbcTemplate.queryForObject(query, new LeavingRequestLogRowMapper());

        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getOneLeavingRequest] [%s] ex ", StringUtil.toJsonString(model)), e);
        }
        return null;
    }

    @Override
    public void increasePendingLeavingRequestCounter(UpdateLeavingRequestStatusModel model, String companyId) {

        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(TABLE_PREFIX, model.yyyyMM, companyId));

        // new log insert
        if (logCounter == null) {
            int newCount = 1;
            logCounter = new LogCounter(TABLE_PREFIX, model.yyyyMM, companyId, newCount);
        } else {
            // update current counter
            int newCount = logCounter.getCount() + 1;
            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }

    private List<String> getListTableLeavingRequestLog(String year) {
        List<String> result = new ArrayList<>();
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    "LeavingRequestLog" + year + "%", null);

            while (rs.next()) {
                result.add(rs.getString("TABLE_NAME"));
            }
        } catch (Exception e) {
            log.error("[getListTableLeavingRequestLog] [{}]", year, e);
        }
        return result;
    }
}
