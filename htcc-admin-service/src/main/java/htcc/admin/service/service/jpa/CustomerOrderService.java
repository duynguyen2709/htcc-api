package htcc.admin.service.service.jpa;

import htcc.admin.service.component.hazelcast.HazelcastLoader;
import htcc.admin.service.config.DbStaticConfigMap;
import htcc.admin.service.jpa.CustomerOrderRepository;
import htcc.common.entity.order.CustomerOrder;
import htcc.common.service.BaseJPAService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class CustomerOrderService extends BaseJPAService<CustomerOrder, String> {

    @Autowired
    private CustomerOrderRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<CustomerOrder> findAll() {
        return new ArrayList<>(DbStaticConfigMap.CUSTOMER_ORDER_MAP.values());
    }

    @Override
    public CustomerOrder findById(String key) {
        return DbStaticConfigMap.CUSTOMER_ORDER_MAP.getOrDefault(key, null);
    }

    @Override
    public CustomerOrder create(CustomerOrder entity) {
        CustomerOrder newEnt = repo.save(entity);
        hazelcastLoader.loadCustomerOrderMap();
        return newEnt;
    }

    @Override
    public CustomerOrder update(CustomerOrder entity) {
        return create(entity);
    }

    @Override
    public void delete(String key) {
        repo.deleteById(key);
        hazelcastLoader.loadCustomerOrderMap();
    }
}
