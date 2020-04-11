package htcc.log.service.mapper;

import htcc.common.entity.complaint.ComplaintLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComplaintLogRowMapper implements RowMapper<ComplaintLogEntity> {

    @Override
    public ComplaintLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        ComplaintLogEntity entity = new ComplaintLogEntity();
        entity.complaintId = rs.getString("complaintId");
        entity.requestId = rs.getString("requestId");
        entity.companyId = rs.getString("companyId");
        entity.username = rs.getString("username");
        entity.clientTime = rs.getLong("clientTime");
        entity.receiverType = rs.getInt("receiverType");
        entity.isAnonymous = rs.getInt("isAnonymous");
        entity.category = rs.getString("category");
        entity.content = rs.getString("content");
        entity.images = rs.getString("images");
        entity.response = rs.getString("response");
        entity.status = rs.getInt("status");
        return entity;
    }
}
