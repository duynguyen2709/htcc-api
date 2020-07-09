package htcc.log.service.repository;

import htcc.common.entity.order.OrderLogEntity;
import htcc.common.entity.order.UpdateOrderStatusModel;

import java.util.List;

public interface OrderLogRepository {

    List<OrderLogEntity> getOrderLog(String yyyyMM);

    void updateOrderLogStatus(UpdateOrderStatusModel model);

    OrderLogEntity getOrder(UpdateOrderStatusModel model);

    void increasePendingOrderCounter(UpdateOrderStatusModel model);
}
