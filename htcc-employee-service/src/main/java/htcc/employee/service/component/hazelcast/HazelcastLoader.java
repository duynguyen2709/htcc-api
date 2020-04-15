package htcc.employee.service.component.hazelcast;

import htcc.common.component.HazelcastService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.entity.jpa.Company;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.service.jpa.CompanyService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

import static htcc.employee.service.config.DbStaticConfigMap.COMPANY_MAP;

@Component
@Log4j2
public class HazelcastLoader {

    @Autowired
    private HazelcastService hazelcastService;

    @Autowired
    private CompanyService companyService;

    @PostConstruct
    public void loadAllStaticMap(){
        log.info("####### Started Loading Static Config Map ########\n");

        loadCompanyMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadCompanyMap(){
        if (COMPANY_MAP != null) {
            COMPANY_MAP.clear();
            COMPANY_MAP = null;
        }

        Map<String, Company> map = new HashMap<>();

        companyService.findAll().forEach(c -> map.put(c.getCompanyId(),c));

        COMPANY_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY);
        log.info("[loadCompanyMap] COMPANY_MAP loaded succeed");
    }
}
