package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.entity.shift.ShiftArrangementLogEntity;
import htcc.common.entity.shift.ShiftTime;
import htcc.common.util.DateTimeUtil;
import htcc.log.service.mapper.ShiftArrangementLogRowMapper;
import htcc.log.service.repository.ShiftArrangementLogRepository;
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
public class ShiftArrangementLogRepositoryImpl implements ShiftArrangementLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    private static final String TABLE_PREFIX = "ShiftArrangementLog";

    private static final Map<String, String> MAP_TABLE_LOG = new HashMap<>();

    @PostConstruct
    private void initListTableLog() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    TABLE_PREFIX + "%", null);

            while (rs.next()) {
                MAP_TABLE_LOG.put(rs.getString("TABLE_NAME"), "");
            }
        } catch (Exception e) {
            log.error("[initListTableLog]", e);
        }
    }

    @Override
    public List<ShiftArrangementLogEntity> getListShiftArrangementLog(String companyId, int week) {
        try {
            final String yyyyMM    = DateTimeUtil.getDateStringFromWeek(week, "yyyyMM");
            final String tableName = String.format("%s%s", TABLE_PREFIX, yyyyMM);
            final String query     = String.format("SELECT * FROM %s WHERE companyId = ? AND week = ?", tableName);

            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                log.warn("Table {} does not exist", tableName);
                return new ArrayList<>();
            }

            return jdbcTemplate.query(query, new Object[] {companyId, week},
                    new ShiftArrangementLogRowMapper());
        } catch (Exception e) {
            log.error("[getListShiftArrangementLog] [{} - {}] ex", companyId, week, e);
            return null;
        }
    }

    @Override
    public List<ShiftArrangementLogEntity> getListShiftArrangementLogByEmployee(String companyId, String username,
                                                                                String arrangeDate) {
        try {
            final String yyyyMM    = arrangeDate.substring(0, 6);
            final String tableName = String.format("%s%s", TABLE_PREFIX, yyyyMM);
            final String query     = String.format("SELECT * FROM %s WHERE companyId = ? AND username = ? AND arrangeDate = ?", tableName);

            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                log.warn("Table {} does not exist", tableName);
                return new ArrayList<>();
            }

            return jdbcTemplate.query(query, new Object[] {companyId, username, arrangeDate},
                    new ShiftArrangementLogRowMapper());
        } catch (Exception e) {
            log.error("[getListShiftArrangementLogByEmployee] [{} - {} - {}] ex", companyId, username, arrangeDate, e);
            return null;
        }
    }

    @Override
    public ShiftArrangementLogEntity getOneShiftArrangementLog(String arrangementId) {
        try {
            final String month     = arrangementId.substring(0, 6);
            final String tableName = String.format("%s%s", TABLE_PREFIX, month);

            final String query = String.format("SELECT * FROM %s WHERE arrangementId = '%s'",
                    tableName, arrangementId);

            return jdbcTemplate.queryForObject(query, new ShiftArrangementLogRowMapper());
        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (Exception e) {
            log.error(String.format("[getOneShiftArrangementLog] [%s] ex ", arrangementId), e);
        }
        return null;
    }

    @Override
    public int deleteShiftArrangementLog(String arrangementId) {
        try {
            final String month     = arrangementId.substring(0, 6);
            final String tableName = String.format("%s%s", TABLE_PREFIX, month);

            final String query = String.format("DELETE FROM %s WHERE arrangementId = ?", tableName);

            return jdbcTemplate.update(query, arrangementId);
        } catch (Exception e) {
            log.error(String.format("[deleteShiftArrangementLog] [%s] ex ", arrangementId), e);
        }

        return 0;
    }

    @Override
    @Transactional
    public int deleteShiftArrangementLog(ShiftTime shiftTime) {
        final long   now       = System.currentTimeMillis();
        final String month     = DateTimeUtil.parseTimestampToString(now, "yyyyMM");
        final String today     = DateTimeUtil.parseTimestampToString(now, "yyyyMMdd");
        final String tableName = String.format("%s%s", TABLE_PREFIX, month);

        final String query = String.format("DELETE FROM %s WHERE companyId = ? AND officeId = ? AND shiftId = ? " +
                "AND arrangeDate = ?", tableName);

        return jdbcTemplate.update(query, shiftTime.getCompanyId(), shiftTime.getOfficeId(), shiftTime.getShiftId(), today);
    }
}
