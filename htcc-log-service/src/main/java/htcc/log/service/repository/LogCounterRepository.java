package htcc.log.service.repository;

import htcc.log.service.entity.jpa.LogCounter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogCounterRepository extends JpaRepository<LogCounter, LogCounter.Key> {

    List<LogCounter> findByLogTypeAndParams(String logType, String params);
}
