package htcc.log.service.repository.impl;

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

import java.util.List;

@Repository
@Log4j2
public class ShiftArrangementLogRepositoryImpl implements ShiftArrangementLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String TABLE_PREFIX = "ShiftArrangementLog";

    @Override
    public List<ShiftArrangementLogEntity> getListShiftArrangementLog(String companyId, int week) {
        try {
            final String yyyyMM    = DateTimeUtil.getDateStringFromWeek(week, "yyyyMM");
            final String tableName = String.format("%s%s", TABLE_PREFIX, yyyyMM);
            final String query     = String.format("SELECT * FROM %s WHERE companyId = ? AND week = ?", tableName);

            return jdbcTemplate.query(query, new Object[] {companyId, week},
                    new ShiftArrangementLogRowMapper());
        } catch (Exception e) {
            log.error("[getListShiftArrangementLog] [{} - {}] ex", companyId, week, e);
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
