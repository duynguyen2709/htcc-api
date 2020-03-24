package htcc.log.service.repository.impl;

import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.CheckOutLogEntity;
import htcc.log.service.mapper.CheckInLogRowMapper;
import htcc.log.service.mapper.CheckOutLogRowMapper;
import htcc.log.service.repository.CheckInLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
public class CheckInLogRepositoryImpl implements CheckInLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public CheckInLogEntity getCheckInLog(String companyId, String username, String ymd) {
        try {
            String tableName = "CheckInLog" + ymd.substring(0, 6);

            String query = String.format("SELECT * FROM %s WHERE companyId='%s' " +
                            "AND username='%s' AND ymd='%s'",
                    tableName, companyId, username, ymd);

            return jdbcTemplate.queryForObject(query, new CheckInLogRowMapper());

        } catch (IncorrectResultSizeDataAccessException e){
            log.warn(String.format("[getCheckInLog] [%s-%s-%s] ex %s", companyId, username, ymd, e.getMessage()));
            return null;
        } catch (Exception e) {
            log.error(String.format("[getCheckInLog] [%s-%s-%s] ex ", companyId, username, ymd), e);
            return null;
        }
    }

    @Override
    public CheckOutLogEntity getCheckOutLog(String companyId, String username, String ymd) {
        try {
            String tableName = "CheckOutLog" + ymd.substring(0, 6);

            String query = String.format("SELECT * FROM %s WHERE companyId='%s' " +
                            "AND username='%s' AND ymd='%s'",
                    tableName, companyId, username, ymd);

            return jdbcTemplate.queryForObject(query, new CheckOutLogRowMapper());

        }  catch (IncorrectResultSizeDataAccessException e){
            log.warn(String.format("[getCheckOutLog] [%s-%s-%s] ex %s", companyId, username, ymd, e.getMessage()));
            return null;
        } catch (Exception e) {
            log.error(String.format("[getCheckOutLog] [%s-%s-%s] ex ", companyId, username, ymd), e);
            return null;
        }
    }
}
