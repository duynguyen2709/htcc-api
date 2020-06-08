package htcc.log.service.mapper;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.shift.ShiftArrangementLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftArrangementLogRowMapper implements RowMapper<ShiftArrangementLogEntity> {

    @Override
    public ShiftArrangementLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        ShiftArrangementLogEntity entity = new ShiftArrangementLogEntity();
        entity.arrangementId = rs.getString("arrangementId");
        entity.actionTime = rs.getLong("actionTime");
        entity.week = rs.getInt("week");
        entity.arrangeDate = rs.getString("arrangeDate");
        entity.requestId = rs.getString("requestId");
        entity.companyId = rs.getString("companyId");
        entity.officeId = rs.getString("officeId");
        entity.username = rs.getString("username");
        entity.shiftId = rs.getString("shiftId");
        entity.startTime = rs.getString("startTime");
        entity.endTime = rs.getString("endTime");
        entity.dayCount = rs.getFloat("dayCount");
        entity.allowDiffTime = rs.getInt("allowDiffTime");
        entity.allowLateMinutes = rs.getInt("allowLateMinutes");
        entity.actor = rs.getString("actor");
        entity.isFixed = rs.getInt("isFixed");
        return entity;
    }
}
