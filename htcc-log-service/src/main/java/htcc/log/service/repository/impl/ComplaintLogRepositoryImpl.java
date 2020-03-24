package htcc.log.service.repository.impl;

import htcc.common.entity.log.ComplaintLogEntity;
import htcc.log.service.mapper.ComplaintLogRowMapper;
import htcc.log.service.repository.ComplaintLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class ComplaintLogRepositoryImpl implements ComplaintLogRepository {


    @Autowired
    private JdbcTemplate jdbcTemplate;

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
}
