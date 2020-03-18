package htcc.log.service.repository;

import htcc.common.entity.base.RequestLogEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

@Repository
public class RequestLogDAO {
    private SimpleJdbcInsert simpleJdbcInsert;

    private JdbcTemplate jdbcTemplate;

    @Autowired
    private DataSource dataSource;

    @Value(value = "${timezone}")
    private String timeZone;

    public void setTableName(String tableName){
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName
                + "("
                + "`logId` int NOT NULL AUTO_INCREMENT,"
                + "`ymd` bigint NOT NULL DEFAULT '0',"
                + "`serviceId` int NOT NULL DEFAULT '0',"
                + "`requestURL` varchar(256) NOT NULL DEFAULT '',"
                + "`method` varchar(8) NOT NULL DEFAULT '',"
                + "`path` varchar(256) NOT NULL DEFAULT '',"
                + "`params` varchar(256) NOT NULL DEFAULT '',"
                + "`body` varchar(4096) NOT NULL DEFAULT '',"
                + "`response` text NOT NULL,"
                + "`returnCode` int NOT NULL DEFAULT '1',"
                + "`requestTime` bigint NOT NULL DEFAULT '0',"
                + "`responseTime` bigint NOT NULL DEFAULT '0',"
                + "`userIP` varchar(32) DEFAULT '',"
                + "`updDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"
                + "PRIMARY KEY (`logId`)"
                + ")";
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.execute(createTableQuery);
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource).withTableName(tableName);
    }

    public int addRequestLog(final RequestLogEntity requestLogEntity) {
        final Map<String, Object> parameters = new HashMap<>();
        // Date Parse
        long datetime = requestLogEntity.getRequestTime();
        Timestamp ts = new Timestamp(datetime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        formatter.setTimeZone(TimeZone.getTimeZone(timeZone));

        parameters.put("ymd", Long.parseLong(formatter.format(ts)));
        parameters.put("serviceId", requestLogEntity.getServiceId());
        parameters.put("requestURL", requestLogEntity.getServiceId());
        parameters.put("method", requestLogEntity.getMethod());
        parameters.put("path", requestLogEntity.getPath());
        parameters.put("params", requestLogEntity.getParams());
        parameters.put("body", requestLogEntity.getBody());
        parameters.put("response", requestLogEntity.getResponse());
        parameters.put("returnCode", requestLogEntity.getReturnCode());
        parameters.put("requestTime", requestLogEntity.getRequestTime());
        parameters.put("responseTime", requestLogEntity.getResponseTime());

        return simpleJdbcInsert.execute(parameters);
    }


}
