package htcc.log.service.repository.impl;

import htcc.common.entity.log.LeavingRequestLogEntity;
import htcc.log.service.mapper.LeavingRequestLogRowMapper;
import htcc.log.service.repository.LeavingRequestLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
@Log4j2
public class LeavingRequestLogRepositoryImpl implements LeavingRequestLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Override
    public List<LeavingRequestLogEntity> getLeavingRequestLog(String companyId, String username, String year) {
        List<LeavingRequestLogEntity> result = new ArrayList<>();
        List<String> tableNames = getListTableLeavingRequestLog(year);
        for (String table : tableNames) {
            try {
                String query = String.format("SELECT * FROM %s WHERE companyId='%s' AND username='%s'",
                                        table, companyId, username);
                result.addAll(jdbcTemplate.query(query, new LeavingRequestLogRowMapper()));
            } catch (Exception e){
                log.error("[getLeavingRequestLog] [{}-{}-{}]", table, companyId, username, e);
            }
        }
        return result;
    }

    private List<String> getListTableLeavingRequestLog(String year){
        List<String> result = new ArrayList<>();
        try {
            DatabaseMetaData md = dataSource.getConnection().getMetaData();
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
