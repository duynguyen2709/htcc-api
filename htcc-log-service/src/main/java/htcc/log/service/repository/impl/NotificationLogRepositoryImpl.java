package htcc.log.service.repository.impl;

import htcc.common.entity.notification.NotificationLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.NotificationLogRowMapper;
import htcc.log.service.repository.NotificationLogRepository;
import htcc.log.service.service.jpa.LogCounterService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
@Log4j2
public class NotificationLogRepositoryImpl implements NotificationLogRepository {

    private static final String TABLE_LOG = "NotificationLog";

    @Autowired
    private LogCounterService logCounterService;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<NotificationLogEntity> getListNotification(int clientId, String companyId, String username,
                                                           int startIndex, int size) {
        List<NotificationLogEntity> res = new ArrayList<>();
        try {
            Date date = new Date(System.currentTimeMillis());
            int indexMonth = 0;
            do {
                String month  = DateTimeUtil.subtractMonthFromDate(date, indexMonth);
                String params = String.format("%s-%s-%s", clientId, companyId, username);

                LogCounter logCounter = logCounterService.findById(new LogCounter.Key(TABLE_LOG, month, params));
                if (logCounter == null) {
                    break;
                }

                if (startIndex >= logCounter.getCount()) {
                    startIndex -= logCounter.getCount();
                    indexMonth++;
                    continue;
                }

                String query = String.format("SELECT * FROM %s%s WHERE clientId = '%s' AND companyId = '%s' " +
                        "AND username = '%s' LIMIT %s,%s", TABLE_LOG, month, clientId, companyId, username, startIndex, size);

                List<NotificationLogEntity> temp = jdbcTemplate.query(query, new NotificationLogRowMapper());

                // [!important - DO NOT DELETE THIS LINE]
                // if reach start index, then set it to 0 to prevent skipping on next loop
                startIndex = 0;

                int index = 0;
                while (res.size() < size && index < temp.size()){
                    res.add(temp.get(index++));
                }

                if (res.size() < size){
                    indexMonth++;
                } else {
                    break;
                }
            } while (true);

        } catch (Exception e){
            log.error("[getListNotification] [{} - {} - {} - {} - {}] ex",
                    clientId, companyId, username, startIndex, size, e);
        }

        return res;
    }
}
