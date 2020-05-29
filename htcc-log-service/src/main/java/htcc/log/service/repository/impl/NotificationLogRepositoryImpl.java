package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.entity.notification.NotificationModel;
import htcc.common.entity.notification.UpdateNotificationReadStatusModel;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.NotificationLogRowMapper;
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
public class NotificationLogRepositoryImpl implements NotificationLogRepository {

    private static final String TABLE_LOG = "NotificationLog";

    private static final String NON_READ_PREFIX = "NonRead";

    public static final Map<String, String> MAP_TABLE_NOTIFICATION_LOG = new HashMap<>();

    @Autowired
    private BaseLogDAO baseLogDAO;

    @PostConstruct
    private void initListTableNotificationLog() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    TABLE_LOG + "%", null);

            while (rs.next()) {
                MAP_TABLE_NOTIFICATION_LOG.put(rs.getString("TABLE_NAME"), "");
            }
        } catch (Exception e) {
            log.error("[initListTableNotificationLog]", e);
        }
    }

    @Autowired
    private LogCounterService logCounterService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private HikariDataSource dataSource;

    @Override
    public List<NotificationLogEntity> getListNotification(int clientId, String companyId, String username,
                                                           int startIndex, int size) {
        List<NotificationLogEntity> res = new ArrayList<>();
        try {
            Date date       = new Date(System.currentTimeMillis());
            int  indexMonth = 0;
            do {
                String month  = DateTimeUtil.subtractMonthFromDate(date, indexMonth);
                String params = String.format("%s-%s-%s", clientId, companyId, username);

                LogCounter logCounter = logCounterService.findById(new LogCounter.Key(TABLE_LOG, month, params));
                if (logCounter == null) {
                    if (isCurrentMonth(month)) {
                        logCounter = createLogCounter(clientId, companyId, username, month);
                    } else {
                        break;
                    }
                }

                if (startIndex >= logCounter.getCount()) {
                    startIndex -= logCounter.getCount();
                    indexMonth++;
                    continue;
                }

                String query = String.format("SELECT * FROM %s%s WHERE targetClientId = ? AND companyId = ? " +
                        "AND username = ? ORDER BY ymd DESC, sendTime DESC LIMIT %s,%s",
                        TABLE_LOG, month, startIndex, size);

                List<NotificationLogEntity> temp = jdbcTemplate.query(query,
                        new Object[] {clientId, companyId, username}, new NotificationLogRowMapper());

                // [!important - DO NOT DELETE THIS LINE]
                // if reach start index, then set it to 0 to prevent skipping on next loop
                startIndex = 0;

                int index = 0;
                while (res.size() < size && index < temp.size()) {
                    res.add(temp.get(index++));
                }

                if (res.size() < size) {
                    indexMonth++;
                } else {
                    break;
                }
            } while (true);

        } catch (Exception e) {
            log.error("[getListNotification] [{} - {} - {} - {} - {}] ex", clientId, companyId, username,
                    startIndex, size, e);
        }

        return res;
    }

    private boolean isCurrentMonth(String month) {
        String thisMonth = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMM");
        return thisMonth.equals(month);
    }

    @Override
    @Transactional
    public void updateReadAllNotification(UpdateNotificationReadStatusModel model) {
        Date         date         = new Date(System.currentTimeMillis());
        int          indexMonth   = 0;
        String       month        = "";
        String       tableName    = "";
        boolean      isTableExist = false;
        List<String> queries      = new ArrayList<>();

        do {
            month = DateTimeUtil.subtractMonthFromDate(date, indexMonth++);
            tableName = String.format("%s%s", TABLE_LOG, month);
            isTableExist = checkTableExist(tableName);
            if (!isTableExist) {
                break;
            }

            String query = String.format( "UPDATE %s SET hasRead = 1 " +
                    "WHERE targetClientId = '%s' AND companyId = '%s' AND username = '%s'",
                    tableName, model.getClientId(), model.getCompanyId(), model.getUsername());

            queries.add(query);
        } while (true);

        log.info("[updateReadAllNotification] batchUpdate [{}]", StringUtil.toJsonString(queries));

        jdbcTemplate.batchUpdate(queries.toArray(new String[0]));

        // update non read counter
        String params = String.format("%s-%s-%s", model.getClientId(), model.getCompanyId(), model.getUsername());
        LogCounter logCounter = new LogCounter(NON_READ_PREFIX + TABLE_LOG, "", params, 0);
        logCounterService.create(logCounter);
    }

    @Override
    @Transactional
    public void updateReadOneNotification(UpdateNotificationReadStatusModel model) {
        NotificationLogEntity logEntity =
                getOneNotification(model.getNotiId(), model.getClientId(), model.getCompanyId(), model.getUsername());
        if (logEntity == null || logEntity.hasRead == 1) {
            log.error("[updateReadOneNotification] entity = {}", StringUtil.toJsonString(logEntity));
            return;
        }

        String month     = model.getNotiId().substring(0, 6);
        String tableName = String.format("%s%s", TABLE_LOG, month);

        String query = String.format("UPDATE %s SET hasRead = 1 WHERE notiId = '%s' AND targetClientId = '%s'" +
                " AND companyId = '%s' AND username = '%s'", tableName, model.getNotiId(), model.getClientId(),
                model.getCompanyId(), model.getUsername());

        jdbcTemplate.update(query);

        // update non read counter
        String params = String.format("%s-%s-%s", model.getClientId(), model.getCompanyId(), model.getUsername());
        LogCounter logCounter = logCounterService.findById(new LogCounter.Key(NON_READ_PREFIX + TABLE_LOG, "", params));
        if (logCounter == null) {
            logCounter = createNonReadLogCounter(model.getClientId(), model.getCompanyId(), model.getUsername());
        } else {
            if (logCounter.getCount() == 0) {
                return;
            }

            logCounter.count -= 1;
            logCounterService.update(logCounter);
        }
    }

    @Override
    public int countUnreadNotifications(int clientId, String companyId, String username) {
        try {
            String params = String.format("%s-%s-%s", clientId, companyId, username);
            LogCounter logCounter =
                    logCounterService.findById(new LogCounter.Key(NON_READ_PREFIX + TABLE_LOG, "", params));
            if (logCounter != null) {
                return logCounter.getCount();
            }
        } catch (Exception e) {
            log.error("[countUnreadNotifications] [{} - {} - {}]", clientId, companyId, username, e);
        }
        return 0;
    }

    @Override
    @Transactional
    public void saveNotification(NotificationModel model) {
        NotificationLogEntity logEntity =
                getOneNotification(model.getNotiId(), model.getTargetClientId(), model.getCompanyId(), model.getUsername());

        if (logEntity == null) {
            createNewNotification(model);
            return;
        }

        String month     = model.getNotiId().substring(0, 6);
        String tableName = String.format("%s%s", TABLE_LOG, month);

        String query = String.format(
                "UPDATE %s SET status = '%s', retryTime = '%s'" + " WHERE notiId = '%s' AND targetClientId = '%s'" +
                        " AND companyId = '%s' AND username = '%s'", tableName, model.getStatus(),
                model.getRetryTime(), model.getNotiId(), model.getTargetClientId(), model.getCompanyId(),
                model.getUsername());

        jdbcTemplate.update(query);
    }

    @Override
    public void createNewNotification(NotificationModel model) {
        NotificationLogEntity logEntity = new NotificationLogEntity(model);
        baseLogDAO.insertLog(logEntity);

        String params = String.format("%s-%s-%s", model.getTargetClientId(), model.getCompanyId(), model.getUsername());
        LogCounter logCounter =
                logCounterService.findById(new LogCounter.Key(TABLE_LOG,
                        DateTimeUtil.parseTimestampToString(model.getSendTime(), "yyyyMM"), params));

        if (logCounter == null) {
            logCounter =
                    createLogCounter(model.getTargetClientId(), model.getCompanyId(), model.getUsername(),
                            DateTimeUtil.parseTimestampToString(model.getSendTime(), "yyyyMM"));
        }
        else {
            logCounter.setCount(logCounter.getCount() + 1);
            logCounter = logCounterService.update(logCounter);
        }

        LogCounter nonReadLogCounter =
                logCounterService.findById(new LogCounter.Key(NON_READ_PREFIX + TABLE_LOG, "", params));
        if (nonReadLogCounter == null) {
            nonReadLogCounter = createNonReadLogCounter(model.getTargetClientId(), model.getCompanyId(), model.getUsername());
        }
        else {
            nonReadLogCounter.setCount(nonReadLogCounter.getCount() + 1);
            nonReadLogCounter = logCounterService.update(nonReadLogCounter);
        }
    }

    private NotificationLogEntity getOneNotification(String notiId, int clientId, String companyId, String username) {
        try {
            final String month     = notiId.substring(0, 6);
            final String tableName = String.format("%s%s", TABLE_LOG, month);

            final String query = String.format("SELECT * FROM %s WHERE notiId = '%s' AND targetClientId = '%s'" +
                    " AND companyId = '%s' AND username = '%s'", tableName, notiId, clientId, companyId, username);

            return jdbcTemplate.queryForObject(query, new NotificationLogRowMapper());
        } catch (IncorrectResultSizeDataAccessException ignored) {
        } catch (Exception e) {
            log.error(String.format("[getOneNotification] [%s-%s-%s-%s] ex ", notiId, clientId, companyId, username),
                    e);
        }
        return null;
    }

    private LogCounter createNonReadLogCounter(int clientId, String companyId, String username) {
        String     params     = String.format("%s-%s-%s", clientId, companyId, username);
        LogCounter logCounter = new LogCounter(NON_READ_PREFIX + TABLE_LOG, "", params, 0);

        Date    date         = new Date(System.currentTimeMillis());
        int     indexMonth   = 0;
        String  month        = "";
        String  tableName    = "";
        boolean isTableExist = false;

        do {
            month = DateTimeUtil.subtractMonthFromDate(date, indexMonth++);
            tableName = String.format("%s%s", TABLE_LOG, month);
            isTableExist = checkTableExist(tableName);
            if (!isTableExist) {
                break;
            }

            String query = String.format("SELECT count(*) FROM %s WHERE hasRead = '0' AND" +
                    " targetClientId = '%s' AND companyId = '%s' AND username = '%s'", tableName, clientId, companyId,
                    username);

            Integer count = jdbcTemplate.queryForObject(query, Integer.class);
            if (count != null) {
                logCounter.count += count;
            }
        } while (true);

        return logCounterService.create(logCounter);
    }

    private LogCounter createLogCounter(int clientId, String companyId, String username, String yyyyMM) {
        String     params     = String.format("%s-%s-%s", clientId, companyId, username);
        LogCounter logCounter = new LogCounter(TABLE_LOG, yyyyMM, params, 0);

        String tableName = String.format("%s%s", TABLE_LOG, yyyyMM);

        String query = String.format(
                "SELECT count(*) FROM %s WHERE targetClientId = '%s' AND companyId = '%s' AND username = '%s'",
                tableName, clientId, companyId, username);

        Integer count = jdbcTemplate.queryForObject(query, Integer.class);
        if (count != null) {
            logCounter.count = count;
        }

        return logCounterService.create(logCounter);
    }

    private boolean checkTableExist(String tableName) {
        return (MAP_TABLE_NOTIFICATION_LOG.containsKey(tableName));
    }
}
