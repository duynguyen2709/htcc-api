package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.constant.ComplaintStatusEnum;
import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;
import htcc.common.entity.checkin.CheckinModel;
import htcc.common.entity.checkin.UpdateCheckInStatusModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.CheckInLogRowMapper;
import htcc.log.service.mapper.CheckOutLogRowMapper;
import htcc.log.service.repository.CheckInLogRepository;
import htcc.log.service.service.jpa.LogCounterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class CheckInLogRepositoryImpl implements CheckInLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    @Autowired
    private LogCounterService logCounterService;

    private static final String CHECKIN_TABLE_PREFIX = "CheckInLog";
    private static final String CHECKOUT_TABLE_PREFIX = "CheckOutLog";

    private static final Map<String, String> MAP_TABLE_LOG = new HashMap<>();

    @PostConstruct
    private void initListTableLog() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    CHECKIN_TABLE_PREFIX + "%", null);

            while (rs.next()) {
                MAP_TABLE_LOG.put(rs.getString("TABLE_NAME"), "");
            }

            ResultSet rs2 = md.getTables(null, null,
                    CHECKOUT_TABLE_PREFIX + "%", null);

            while (rs2.next()) {
                MAP_TABLE_LOG.put(rs2.getString("TABLE_NAME"), "");
            }
        } catch (Exception e) {
            log.error("[initListTableLog]", e);
        }
    }

    @Override
    public List<CheckInLogEntity> getCheckInLog(String companyId, String username, String ymd) {
        try {
            final String tableName = "CheckInLog" + ymd.substring(0, 6);
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }

            final String query = String.format("SELECT * FROM %s WHERE companyId = ? " +
                            "AND username = ? AND ymd = ?", tableName);

            return jdbcTemplate.query(query, new Object[] {companyId, username, ymd},
                    new CheckInLogRowMapper());
        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getCheckInLog] [%s-%s-%s] ex ", companyId, username, ymd), e);
        }
        return null;
    }

    @Override
    public List<CheckinModel> getPendingLog(String companyId, String yyyyMM) {
        try {
            final String checkInTableName = "CheckInLog" + yyyyMM;
            final String checkOutTableName = "CheckOutLog" + yyyyMM;

            if (!MAP_TABLE_LOG.containsKey(checkInTableName) || !MAP_TABLE_LOG.containsKey(checkOutTableName)) {
                return new ArrayList<>();
            }

            final String checkInQuery = String.format("SELECT * FROM %s WHERE companyId = ?", checkInTableName);
            final String checkOutQuery = String.format("SELECT * FROM %s WHERE companyId = ?", checkOutTableName);

            List<CheckInLogEntity> listCheckInLog = jdbcTemplate.query(checkInQuery, new Object[] {companyId},
                    new CheckInLogRowMapper());
            List<CheckOutLogEntity> listCheckOutLog = jdbcTemplate.query(checkOutQuery, new Object[] {companyId},
                    new CheckOutLogRowMapper());

            List<CheckinModel> response = new ArrayList<>();
            for (CheckInLogEntity logEnt : listCheckInLog) {
                if (logEnt.getStatus() == ComplaintStatusEnum.PROCESSING.getValue() ||
                        !logEnt.getApprover().isEmpty()) {
                    response.add(new CheckinModel(logEnt));
                }
            }
            for (CheckOutLogEntity logEnt : listCheckOutLog) {
                if (logEnt.getStatus() == ComplaintStatusEnum.PROCESSING.getValue() ||
                        !logEnt.getApprover().isEmpty()) {
                    response.add(new CheckinModel(logEnt));
                }
            }
            return response;
        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getPendingLog] [%s-%s] ex ", companyId, yyyyMM), e);
        }
        return null;
    }

    @Override
    public List<CheckOutLogEntity> getCheckOutLog(String companyId, String username, String ymd) {
        try {
            final String tableName = "CheckOutLog" + ymd.substring(0, 6);
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }

            final String query = String.format("SELECT * FROM %s WHERE companyId = ? " +
                    "AND username = ? AND ymd = ?", tableName);

            return jdbcTemplate.query(query, new Object[] {companyId, username, ymd},
                    new CheckOutLogRowMapper());
        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getCheckOutLog] [%s-%s-%s] ex ", companyId, username, ymd), e);
        }
        return null;
    }

    @Override
    public int updateOppositeId(CheckinModel checkOutModel) {
        try {
            final CheckinModel oppositeModel = checkOutModel.getOppositeModel();
            final String tableName = "CheckInLog" + oppositeModel.getDate().substring(0, 6);
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                throw new Exception(String.format("Table %s not found", tableName));
            }

            final String query = String.format("UPDATE %s SET oppositeId = ? WHERE checkInId = ?", tableName);
            return jdbcTemplate.update(query, checkOutModel.getCheckInId(), oppositeModel.getCheckInId());
        } catch (Exception e) {
            log.error("[updateOppositeId] {} ex", StringUtil.toJsonString(checkOutModel), e);
            return -1;
        }
    }

    @Override
    public CheckinModel getOneCheckInLog(UpdateCheckInStatusModel request) {
        try {
            String tableName = "";
            String query = "";

            if (request.getCheckInId().startsWith("CheckIn")) {
                tableName = "CheckInLog" + request.getYyyyMM();
                if (!MAP_TABLE_LOG.containsKey(tableName)) {
                    throw new Exception(String.format("Table %s not found", tableName));
                }
                query = String.format("SELECT * FROM %s WHERE checkInId = ?", tableName);
                CheckInLogEntity logEnt = jdbcTemplate.queryForObject(query, new Object[] {request.getCheckInId()}, new CheckInLogRowMapper());
                if (logEnt != null) {
                    return new CheckinModel(logEnt);
                }
            } else if (request.getCheckInId().startsWith("CheckOut")) {
                tableName = "CheckOutLog" + request.getYyyyMM();
                if (!MAP_TABLE_LOG.containsKey(tableName)) {
                    throw new Exception(String.format("Table %s not found", tableName));
                }
                query = String.format("SELECT * FROM %s WHERE checkInId = ?", tableName);
                CheckOutLogEntity logEnt = jdbcTemplate.queryForObject(query, new Object[] {request.getCheckInId()}, new CheckOutLogRowMapper());
                if (logEnt != null) {
                    return new CheckinModel(logEnt);
                }
            }
        } catch (Exception e) {
            log.error("[getOneCheckInLog] {} ex", StringUtil.toJsonString(request), e);
        }
        return null;
    }

    @Override
    @Transactional
    public int updateCheckInStatus(UpdateCheckInStatusModel request, String companyId) throws Exception {
        String tableName = "";

        if (request.getCheckInId().startsWith("CheckIn")) {
            tableName = CHECKIN_TABLE_PREFIX + request.getYyyyMM();
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                throw new Exception(String.format("Table %s not found", tableName));
            }
        } else if (request.getCheckInId().startsWith("CheckOut")) {
            tableName = "CheckOutLog" + request.getYyyyMM();
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                throw new Exception(String.format("Table %s not found", tableName));
            }
        }
        final String query = String.format("UPDATE %s SET status = ?, approver = ? WHERE checkInId='%s'", tableName, request.getCheckInId());

        int rowAffected = jdbcTemplate.update(query, request.getStatus(), request.getApprover());
        if (rowAffected == 1) {
            decreasePendingCheckInCounter(request, companyId);
        }

        return rowAffected;
    }

    private void decreasePendingCheckInCounter(UpdateCheckInStatusModel model, String companyId) {

        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(CHECKIN_TABLE_PREFIX, model.getYyyyMM(), companyId));

        // new log insert
        if (logCounter == null) {
            List<CheckinModel> list = getPendingLog(companyId, model.getYyyyMM());
            int pendingCount = (int) list.stream()
                    .filter(c -> c.getStatus() == ComplaintStatusEnum.PROCESSING.getValue())
                    .count();

            int newCount = 0;
            if (pendingCount > 0){
                newCount = pendingCount -1 ;
            }

            logCounter = new LogCounter(CHECKIN_TABLE_PREFIX, model.getYyyyMM(), companyId, newCount);
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
    public void increasePendingCheckInCounter(CheckinModel model) {
        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(
                CHECKIN_TABLE_PREFIX, model.getDate().substring(0, 6), model.getCompanyId()));

        // new log insert
        if (logCounter == null) {
            int newCount = 1;
            logCounter = new LogCounter(CHECKIN_TABLE_PREFIX, model.getDate().substring(0, 6), model.getCompanyId(), newCount);
        } else {
            // update current counter
            int newCount = logCounter.getCount() + 1;
            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }
}
