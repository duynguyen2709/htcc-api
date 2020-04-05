package htcc.log.service.service.jpa;

import htcc.common.service.BaseJPAService;
import htcc.log.service.entity.jpa.LogCounter;
import htcc.log.service.repository.LogCounterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LogCounterService extends BaseJPAService<LogCounter, LogCounter.Key> {

    @Autowired
    private LogCounterRepository repo;

    @Override
    public List<LogCounter> findAll() {
        return repo.findAll();
    }

    @Override
    public LogCounter findById(LogCounter.Key key) {
        Optional<LogCounter> log = repo.findById(key);
        return log.orElse(null);
    }

    @Override
    public LogCounter create(LogCounter logCounter) {
        return repo.save(logCounter);
    }

    @Override
    public LogCounter update(LogCounter logCounter) {
        return repo.save(logCounter);
    }

    @Override
    public void delete(LogCounter.Key key) {
        repo.deleteById(key);
    }
}
