package htcc.log.service.repository;

import htcc.common.entity.base.BaseLogEntity;
import htcc.common.util.DateTimeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.TimeZone;

@Repository
public class LogDAO {

    @Autowired
    private DataSource dataSource;

    public int insertLog(BaseLogEntity logEntity) {
        String yyyyMM = DateTimeUtil.parseTimestampToString(logEntity.getCreateTime(), "yyyyMM");
        String tableName = logEntity.retrieveTableName() + yyyyMM;

        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName);

        long ymd = Long.parseLong(DateTimeUtil.parseTimestampToString(logEntity.getCreateTime(), "yyyyMMdd"));

        Map<String, Object> parameters = logEntity.getParamsMap();
        parameters.put("ymd", ymd);
        return simpleJdbcInsert.execute(parameters);
    }
}
