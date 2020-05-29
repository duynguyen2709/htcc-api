package htcc.log.service.service.jpa;

import htcc.common.entity.notification.NotificationBuz;
import htcc.common.service.BaseJPAService;
import htcc.log.service.repository.NotificationBuzRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationBuzService extends BaseJPAService<NotificationBuz, NotificationBuz.Key> {

    @Autowired
    private NotificationBuzRepository repo;

    @Override
    public List<NotificationBuz> findAll() {
        return repo.findAll();
    }

    @Override
    public NotificationBuz findById(NotificationBuz.Key key) {
        return repo.findById(key).orElse(null);
    }

    @Override
    public NotificationBuz create(NotificationBuz notificationBuz) {
        return repo.save(notificationBuz);
    }

    @Override
    public NotificationBuz update(NotificationBuz notificationBuz) {
        return create(notificationBuz);
    }

    @Override
    public void delete(NotificationBuz.Key key) {
        repo.deleteById(key);
    }

    public List<NotificationBuz> findByClientIdAndCompanyId(int clientId, String companyId) {
        return repo.findByClientIdAndCompanyId(clientId, companyId);
    }
}
