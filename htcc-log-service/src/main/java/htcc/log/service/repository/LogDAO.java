package htcc.log.service.repository;

import htcc.common.entity.base.LogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Repository
public class LogDAO {
    @Autowired
    private DataSource dataSource;

    @Value(value = "${timezone}")
    private String timeZone;

    public void createTableIfNotExist(String tableName, String createTableParamsScript){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
               + "("
               + "`logId` int NOT NULL AUTO_INCREMENT,"
               + "`ymd` bigint NOT NULL DEFAULT '0',"
               + createTableParamsScript
               + "PRIMARY KEY (`logId`)"
               + ")";
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(createTableQuery);
    }

    public int addLog(LogEntity logEntity) {
        SimpleJdbcInsert simpleJdbcInsert;
        SimpleDateFormat formatter;
        Map<String, Object> parameters;
        long timestamp;

        timestamp = logEntity.getCreateTime();
        Timestamp ts = new Timestamp(timestamp);

        formatter = new SimpleDateFormat("yyyyMM");
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        String ym = formatter.format(ts);
        String tableName = logEntity.getTableName() + ym;
        this.createTableIfNotExist(tableName, logEntity.getSQLCreateTableSchemaScript());

        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName);

        formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));
        Long ymd = Long.parseLong(formatter.format(ts));
        parameters = logEntity.getHashMap();
        parameters.put("ymd", ymd);
        return simpleJdbcInsert.execute(parameters);
    }
}
