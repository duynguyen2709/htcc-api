package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.OrderLogRowMapper;
import htcc.log.service.repository.OrderLogRepository;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@Log4j2
public class OrderLogRepositoryImpl implements OrderLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LogCounterService logCounterService;

    @Autowired
    private HikariDataSource dataSource;

    private static final String TABLE_PREFIX  = "OrderLog";

    private static final Map<String, String> MAP_TABLE_LOG = new HashMap<>();

    @PostConstruct
    private void initListTableLog() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    TABLE_PREFIX + "%", null);

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                String yyyyMM = tableName.substring(TABLE_PREFIX.length());
                MAP_TABLE_LOG.put(tableName, yyyyMM.substring(0, 4));
            }
        } catch (Exception e) {
            log.error("[initListTableLog]", e);
        }
    }

    @Override
    public List<OrderLogEntity> getOrderLog(String yyyyMM) {
        try {
            final String tableName = TABLE_PREFIX + yyyyMM;
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }
            final String query = String.format("SELECT * FROM %s", tableName);

            return jdbcTemplate.query(query, new OrderLogRowMapper());
        } catch (Exception e) {
            log.error(String.format("[getOrderLog] [%s] ex ", yyyyMM), e);
            return null;
        }
    }

    @Override
    @Transactional
    public void updateOrderLogStatus(UpdateOrderStatusModel model) {
        final String tableName = TABLE_PREFIX + "20" + model.getOrderId().substring(0, 4);
        if (!MAP_TABLE_LOG.containsKey(tableName)) {
            log.error("Table " + tableName + " is not exist");
            return;
        }
        final String query = String.format("UPDATE %s SET orderStatus = ? WHERE orderId = '%s'",
                tableName, model.getOrderId());

        int rowAffected = jdbcTemplate.update(query, model.getStatus());
        if (rowAffected == 1) {
            decreaseOrderLogCounter(model);
        }
    }

    private void decreaseOrderLogCounter(UpdateOrderStatusModel model){
        String yyyyMM = "20" + model.getOrderId().substring(0, 4);
        OrderLogEntity entity = getOrder(model);
        String params = "";

        LogCounter logCounter = logCounterService.findById(
                new LogCounter.Key(TABLE_PREFIX, yyyyMM, params));

        // new log insert
        if (logCounter == null) {
            List<OrderLogEntity> list = getOrderLog(yyyyMM);
            int pendingCount = (int) list.stream()
                    .filter(c -> c.getOrderStatus() == OrderStatusEnum.PAY_SUCCESS.getValue())
                    .count();

            int newCount = 0;
            if (pendingCount > 0){
                newCount = pendingCount -1 ;
            }

            logCounter = new LogCounter(TABLE_PREFIX, yyyyMM, params, newCount);
        } else {
            // update current counter
            int newCount = 0;
            if (logCounter.getCount() > 0) {
                newCount = logCounter.getCount() - 1;
            }

            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }

    @Override
    public void increasePendingOrderCounter(UpdateOrderStatusModel model){
        String yyyyMM = "20" + model.getOrderId().substring(0, 4);
        OrderLogEntity entity = getOrder(model);
        String params = "";

        LogCounter logCounter = logCounterService.findById(
                new LogCounter.Key(TABLE_PREFIX, yyyyMM, params));

        // new log insert
        if (logCounter == null) {
            int newCount = 1;
            logCounter = new LogCounter(TABLE_PREFIX, yyyyMM, params, newCount);
        } else {
            // update current counter
            int newCount = logCounter.getCount() + 1;
            logCounter.setCount(newCount);
        }

        logCounterService.update(logCounter);
    }


    @Override
    public OrderLogEntity getOrder(UpdateOrderStatusModel model) {
        try {
            final String tableName = TABLE_PREFIX + "20" + model.getOrderId().substring(0, 4);
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return null;
            }

            final String query = String.format("SELECT * FROM %s WHERE orderId = '%s'",
                    tableName, model.getOrderId());

            return jdbcTemplate.queryForObject(query, new OrderLogRowMapper());

        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getOrder] [%s] ex ", StringUtil.toJsonString(model)), e);
        }
        return null;
    }
}
