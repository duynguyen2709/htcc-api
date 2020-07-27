package htcc.log.service.repository.impl;

import com.zaxxer.hikari.HikariDataSource;
import htcc.common.constant.OrderStatusEnum;
import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.UpdateOrderStatusModel;
import htcc.common.entity.payslip.ManagerLockSalaryRequest;
import htcc.common.entity.payslip.SalaryLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.mapper.OrderLogRowMapper;
import htcc.log.service.mapper.SalaryLogRowMapper;
import htcc.log.service.repository.OrderLogRepository;
import htcc.log.service.repository.SalaryLogRepository;
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
public class SalaryLogRepositoryImpl implements SalaryLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private LogCounterService logCounterService;

    @Autowired
    private HikariDataSource dataSource;

    private static final String TABLE_PREFIX  = "SalaryLog";

    private static final Map<String, String> MAP_TABLE_LOG = new HashMap<>();

    @PostConstruct
    private void initListTableLog() {
        try (Connection conn = dataSource.getConnection()) {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getTables(null, null,
                    TABLE_PREFIX + "%", null);

            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                MAP_TABLE_LOG.put(tableName, "");
            }
        } catch (Exception e) {
            log.error("[initListTableLog]", e);
        }
    }

    @Override
    public List<SalaryLogEntity> getEmployeePayslip(String companyId, String username, String yyyyMM) {
        try {
            final String tableName = TABLE_PREFIX + yyyyMM;
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }
            final String query = String.format("SELECT * FROM %s WHERE companyId = ? AND username = ? AND status = ?", tableName);

            return jdbcTemplate.query(query,new Object[] {companyId, username, 1}, new SalaryLogRowMapper());
        } catch (Exception e) {
            log.error(String.format("[getEmployeePayslip] [%s-%s-%s] ex ", companyId, username, yyyyMM), e);
            return null;
        }
    }

    @Override
    public List<SalaryLogEntity> getSalaryLogForManager(String companyId, String yyyyMM) {
        try {
            final String tableName = TABLE_PREFIX + yyyyMM;
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                return new ArrayList<>();
            }
            final String query = String.format("SELECT * FROM %s WHERE companyId = ?", tableName);

            return jdbcTemplate.query(query,new Object[] {companyId}, new SalaryLogRowMapper());
        } catch (Exception e) {
            log.error(String.format("[getSalaryLogForManager] [%s] ex ", companyId), e);
            return null;
        }
    }

    @Override
    public int deleteSalaryLog(String yyyyMM, String paySlipId) {
        try {
            final String tableName = TABLE_PREFIX + yyyyMM;
            if (!MAP_TABLE_LOG.containsKey(tableName)) {
                throw new Exception("Table " + tableName + " is not exist");
            }
            final String query = String.format("DELETE FROM %s WHERE paySlipId = ?", tableName);
            return jdbcTemplate.update(query, paySlipId);
        } catch (Exception e) {
            log.error(String.format("[deleteSalaryLog] [%s-%s] ex", yyyyMM, paySlipId), e);
            return -1;
        }
    }

    @Override
    @Transactional
    public void lockSalaryLog(ManagerLockSalaryRequest request) throws Exception {
        final String tableName = TABLE_PREFIX + request.getYyyyMM();
        if (!MAP_TABLE_LOG.containsKey(tableName)) {
            throw new Exception("Table " + tableName + " is not exist");
        }

        String list = "";
        for (String id : request.getPaySlipIdList()) {
            list += ",'" + id + "'";
        }
        list = list.substring(1);

        String lockDate = DateTimeUtil.parseTimestampToString(System.currentTimeMillis(), "yyyyMMdd");

        final String query = String.format("UPDATE %s SET status = ?, actor = ?, lockDate = ? WHERE paySlipId IN (%s) AND status = ?", tableName, list);
        jdbcTemplate.update(query, 1, request.getActor(), lockDate, 0);
    }
}
