package htcc.log.service.repository.impl;

import htcc.common.entity.checkin.CheckInLogEntity;
import htcc.common.entity.checkin.CheckOutLogEntity;
import htcc.log.service.mapper.CheckInLogRowMapper;
import htcc.log.service.mapper.CheckOutLogRowMapper;
import htcc.log.service.repository.CheckInLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Log4j2
public class CheckInLogRepositoryImpl implements CheckInLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<CheckInLogEntity> getCheckInLog(String companyId, String username, String ymd) {
        try {
            final String tableName = "CheckInLog" + ymd.substring(0, 6);

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
    public List<CheckOutLogEntity> getCheckOutLog(String companyId, String username, String ymd) {
        try {
            final String tableName = "CheckOutLog" + ymd.substring(0, 6);

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
}
