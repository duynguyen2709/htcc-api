package htcc.log.service.repository.impl;

import htcc.common.entity.complaint.UpdateComplaintStatusModel;
import htcc.common.entity.log.CheckInLogEntity;
import htcc.common.entity.log.ComplaintLogEntity;
import htcc.common.util.DateTimeUtil;
import htcc.common.util.StringUtil;
import htcc.log.service.mapper.CheckInLogRowMapper;
import htcc.log.service.mapper.ComplaintLogRowMapper;
import htcc.log.service.repository.ComplaintLogRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
@Log4j2
public class ComplaintLogRepositoryImpl implements ComplaintLogRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<ComplaintLogEntity> getComplaintLog(String companyId, String username, String yyyyMM) {
        try {
            String tableName = "ComplaintLog" + yyyyMM;

            String query = String.format("SELECT * FROM %s WHERE companyId='%s' " +
                            "AND username='%s'",
                    tableName, companyId, username);

            return jdbcTemplate.query(query, new ComplaintLogRowMapper());

        } catch (Exception e) {
            log.error(String.format("[getComplaintLog] [%s-%s-%s] ex ", companyId, username, yyyyMM), e);
            return null;
        }
    }

    @Override
    public List<ComplaintLogEntity> getComplaintLogByReceiverType(int receiverType, String yyyyMM) {
        try {
            String tableName = "ComplaintLog" + yyyyMM;

            String query = String.format("SELECT * FROM %s WHERE receiverType='%s'",
                    tableName, receiverType);

            return jdbcTemplate.query(query, new ComplaintLogRowMapper());

        } catch (Exception e) {
            log.error(String.format("[getComplaintLogByReceiverType] [%s-%s] ex ", receiverType, yyyyMM), e);
            return null;
        }
    }


    @Override
    public void updateComplaintLogStatus(UpdateComplaintStatusModel model) {
        try {
            String tableName = "ComplaintLog" + model.getYyyyMM();

            String query = String.format("UPDATE %s SET status = ?, response = ? WHERE complaintId='%s'",
                    tableName, model.getComplaintId());

            jdbcTemplate.update(query, model.getStatus(), model.getResponse());

        } catch (Exception e) {
            log.error(String.format("[updateComplaintLogStatus] [%s] ex ", StringUtil.toJsonString(model)), e);
        }
    }

    @Override
    public ComplaintLogEntity getComplaint(UpdateComplaintStatusModel model) {
        try {
            String tableName = "ComplaintLog" + model.getYyyyMM();

            String query = String.format("SELECT * FROM %s WHERE complaintId='%s'",
                    tableName, model.getComplaintId());

            return jdbcTemplate.queryForObject(query, new ComplaintLogRowMapper());

        } catch (IncorrectResultSizeDataAccessException ignored){
        } catch (Exception e) {
            log.error(String.format("[getComplaint] [%s] ex ", StringUtil.toJsonString(model)), e);
        }
        return null;
    }
}
