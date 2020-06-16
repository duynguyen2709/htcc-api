package htcc.log.service.repository.impl;

import htcc.common.constant.ClientSystemEnum;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.log.service.mapper.NotificationLogRowMapper;
import htcc.log.service.repository.ManagerNotificationLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class ManagerNotificationLogRepositoryImpl implements ManagerNotificationLogRepository {

    private static final String TABLE_LOG = "NotificationLog";

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NotificationLogEntity> getListNotificationLog(String companyId, String sender, String yyyyMMdd) {
        List<NotificationLogEntity> res = new ArrayList<>();
        try {
            final String month = yyyyMMdd.substring(0, 6);
            final String tableName = TABLE_LOG + month;
            final String query = String.format("SELECT * FROM %s WHERE sourceClientId = ? " +
                    "AND companyId = ? AND sender = ? AND ymd = ? ORDER BY sendTime ASC", tableName);

            return jdbcTemplate.query(query,
                    new Object[] { ClientSystemEnum.MANAGER_WEB.getValue(), companyId, sender, yyyyMMdd },
                    new NotificationLogRowMapper());

        } catch (Exception e) {
            log.error("[getListNotificationLog] [{} - {}] ex", yyyyMMdd, sender, e);
            return null;
        }
    }
}
