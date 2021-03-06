package htcc.log.service.mapper;

import htcc.common.entity.complaint.ComplaintLogEntity;
import htcc.common.entity.notification.NotificationLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class NotificationLogRowMapper implements RowMapper<NotificationLogEntity> {

    @Override
    public NotificationLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        NotificationLogEntity entity = new NotificationLogEntity();
        entity.notiId = rs.getString("notiId");
        entity.requestId = rs.getString("requestId");
        entity.sourceClientId = rs.getInt("sourceClientId");
        entity.targetClientId = rs.getInt("targetClientId");
        entity.receiverType = rs.getInt("receiverType");
        entity.sender = rs.getString("sender");
        entity.companyId = rs.getString("companyId");
        entity.officeId = rs.getString("officeId");
        entity.username = rs.getString("username");
        entity.sendTime = rs.getLong("sendTime");
        entity.retryTime = rs.getLong("retryTime");
        entity.title = rs.getString("title");
        entity.content = rs.getString("content");
        entity.iconUrl = rs.getString("iconUrl");
        entity.iconId = rs.getString("iconId");
        entity.screenId = rs.getInt("screenId");
        entity.tokenPush = rs.getString("tokenPush");
        entity.hasRead = rs.getInt("hasRead");
        entity.status = rs.getInt("status");
        return entity;
    }
}
