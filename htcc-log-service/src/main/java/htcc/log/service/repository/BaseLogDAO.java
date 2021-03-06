package htcc.log.service.repository;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Map;

@Repository
@Log4j2
public class BaseLogDAO {

    @Autowired
    private DataSource dataSource;

    public long insertLog(BaseLogEntity logEntity) {
        try {
            String yyyyMM    = DateTimeUtil.parseTimestampToString(logEntity.getCreateTime(), "yyyyMM");
            String yyyyMMdd  = DateTimeUtil.parseTimestampToString(logEntity.getCreateTime(), "yyyyMMdd");
            String tableName = logEntity.retrieveTableName() + yyyyMM;

            SimpleJdbcInsert jdbc = new SimpleJdbcInsert(dataSource)
                    .withTableName(tableName)
                    .usingGeneratedKeyColumns("logId");

            Map<String, Object> parameters = logEntity.getParamsMap();
            parameters.put("ymd", yyyyMMdd);
            parameters.put("updDate", new Timestamp(System.currentTimeMillis()));
            return jdbc.executeAndReturnKey(parameters).longValue();
        } catch (Exception e) {
            log.error("[insertLog] {} ex", StringUtil.toJsonString(logEntity), e);
            return -1;
        }
    }
}
