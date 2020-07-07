package htcc.log.service.mapper;

import htcc.common.entity.leavingrequest.LeavingRequestLogEntity;
import htcc.common.entity.order.OrderLogEntity;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderLogRowMapper implements RowMapper<OrderLogEntity> {

    @Override
    public OrderLogEntity mapRow(ResultSet rs, int i) throws SQLException {
        OrderLogEntity entity = new OrderLogEntity();
        entity.orderId = rs.getString("orderId");
        entity.date = rs.getString("date");
        entity.companyId = rs.getString("companyId");
        entity.email = rs.getString("email");
        entity.lastPaymentDate = rs.getString("lastPaymentDate");
        entity.nextPaymentDate = rs.getString("nextPaymentDate");
        entity.paymentName = rs.getString("paymentName");
        entity.paymentId = rs.getString("paymentId");
        entity.paymentCycleType = rs.getInt("paymentCycleType");
        entity.paymentTime = rs.getLong("paymentTime");
        entity.comboDetail = rs.getString("comboDetail");
        entity.supportedFeatures = rs.getString("supportedFeatures");
        entity.totalPrice = rs.getLong("totalPrice");
        entity.discountPercentage = rs.getFloat("discountPercentage");
        entity.orderStatus = rs.getInt("orderStatus");
        entity.firstPay = rs.getInt("firstPay");
        return entity;
    }
}
