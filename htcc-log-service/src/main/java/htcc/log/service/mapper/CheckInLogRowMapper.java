package htcc.log.service.mapper;

import htcc.common.entity.log.CheckInLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckInLogRowMapper implements RowMapper<CheckInLogEntity> {

    @Override
    public CheckInLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        CheckInLogEntity entity = new CheckInLogEntity();
        entity.requestId = rs.getString("requestId");
        entity.companyId = rs.getString("companyId");
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

        return entity;
    }
}
