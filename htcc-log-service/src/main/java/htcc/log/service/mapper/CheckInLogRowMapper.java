package htcc.log.service.mapper;

import htcc.common.entity.checkin.CheckInLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInLogRowMapper implements RowMapper<CheckInLogEntity> {

    @Override
    public CheckInLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        CheckInLogEntity entity = new CheckInLogEntity();
        entity.requestId = rs.getString("requestId");
        entity.checkInId = rs.getString("checkInId");
        entity.subType = rs.getInt("subType");
        entity.companyId = rs.getString("companyId");
        entity.officeId = rs.getString("officeId");
        entity.username = rs.getString("username");
        entity.clientTime = rs.getLong("clientTime");
        entity.serverTime = rs.getLong("serverTime");
        entity.validTime = rs.getString("validTime");
        entity.isOnTime = rs.getInt("isOnTime") == 1;
        entity.validLatitude = rs.getFloat("validLatitude");
        entity.validLongitude = rs.getFloat("validLongitude");
        entity.latitude = rs.getFloat("latitude");
        entity.longitude = rs.getFloat("longitude");
        entity.maxAllowDistance = rs.getInt("maxAllowDistance");
        entity.usedWifi = rs.getInt("usedWifi") == 1;
        entity.ip = rs.getString("ip");
        entity.image = rs.getString("image");
        entity.reason = rs.getString("reason");
        entity.approver = rs.getString("approver");
        entity.status = rs.getInt("status");
        entity.shiftTime = rs.getString("shiftTime");
        entity.oppositeId = rs.getString("oppositeId");

        return entity;
    }
}
