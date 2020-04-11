package htcc.log.service.mapper;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LeavingRequestLogRowMapper implements RowMapper<LeavingRequestLogEntity> {

    @Override
    public LeavingRequestLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        LeavingRequestLogEntity entity = new LeavingRequestLogEntity();
        entity.leavingRequestId = rs.getString("leavingRequestId");
        entity.requestId = rs.getString("requestId");
        entity.companyId = rs.getString("companyId");
        entity.username = rs.getString("username");
        entity.clientTime = rs.getLong("clientTime");
        entity.useDayOff = rs.getInt("useDayOff");
        entity.category = rs.getString("category");
        entity.reason = rs.getString("reason");
        entity.detail = rs.getString("detail");
        entity.response = rs.getString("response");
        entity.approver = rs.getString("approver");
        entity.status = rs.getInt("status");
        return entity;
    }
}
