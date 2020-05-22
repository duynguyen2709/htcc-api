package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.NotificationLogRowMapper;
import htcc.log.service.repository.AdminNotificationLogRepository;
import htcc.log.service.repository.BaseLogDAO;
import htcc.log.service.repository.NotificationLogRepository;
import htcc.log.service.service.jpa.LogCounterService;
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
import java.util.*;

@Repository
@Log4j2
public class AdminNotificationLogRepositoryImpl implements AdminNotificationLogRepository {

    private static final String TABLE_LOG = "NotificationLog";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NotificationLogEntity> getListNotificationLog(String yyyyMMdd, String sender) {
        List<NotificationLogEntity> res = new ArrayList<>();
        try {
            final String month = yyyyMMdd.substring(0, 6);
            final String tableName = TABLE_LOG + month;
            final String query = String.format("SELECT * FROM %s WHERE sourceClientId = ? " +
                    "AND sender = ? AND ymd = ?", tableName);

            if (!NotificationLogRepositoryImpl.MAP_TABLE_NOTIFICATION_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }

            return jdbcTemplate.query(query,
                    new Object[] { ClientSystemEnum.ADMIN_WEB.getValue(), sender, yyyyMMdd },
                    new NotificationLogRowMapper());

        } catch (Exception e) {
            log.error("[getListNotificationLog] [{} - {}] ex", yyyyMMdd, sender, e);
            return null;
        }
    }
}
