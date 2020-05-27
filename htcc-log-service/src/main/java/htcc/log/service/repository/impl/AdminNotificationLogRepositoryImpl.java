package htcc.log.service.repository.impl;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.log.service.mapper.NotificationLogRowMapper;
import htcc.log.service.repository.AdminNotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

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
                    "AND sender = ? AND ymd = ? ORDER BY sendTime ASC", tableName);

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
