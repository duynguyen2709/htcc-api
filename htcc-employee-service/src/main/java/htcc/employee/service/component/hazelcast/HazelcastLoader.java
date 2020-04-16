package htcc.employee.service.component.hazelcast;

import com.google.gson.reflect.TypeToken;
import htcc.common.component.HazelcastService;
import htcc.common.constant.CacheKeyEnum;
import htcc.common.constant.Constant;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.util.StringUtil;
import htcc.employee.service.repository.jpa.BuzConfigRepository;
import htcc.employee.service.repository.jpa.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static htcc.employee.service.config.DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP;
import static htcc.employee.service.config.DbStaticConfigMap.COMPANY_MAP;

@Component
@Log4j2
public class HazelcastLoader {

    @Autowired
    private HazelcastService hazelcastService;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private BuzConfigRepository buzConfigRepository;

    @PostConstruct
    public void loadAllStaticMap() throws Exception {
        log.info("####### Started Loading Static Config Map ########\n");

        loadCompanyMap();

        // TODO: LOAD OFFICE MAP
        loadOfficeMap();

        // must be below company map to traverse
        loadCompanyDayOffInfoMap();

        log.info("####### Loaded All Static Config Map Done ########\n");
    }

    public void loadCompanyMap() {
        if (COMPANY_MAP != null) {
            COMPANY_MAP.clear();
            COMPANY_MAP = null;
        }

        Map<String, Company> map = new HashMap<>();

        companyRepository.findAll().forEach(c -> map.put(c.getCompanyId(), c));

        COMPANY_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY);
        log.info("[loadCompanyMap] COMPANY_MAP loaded succeed [{}]", StringUtil.toJsonString(COMPANY_MAP));
    }

    public void loadOfficeMap(){

    }

    public void loadCompanyDayOffInfoMap() throws Exception {
        if (COMPANY_DAY_OFF_INFO_MAP != null) {
            COMPANY_DAY_OFF_INFO_MAP.clear();
            COMPANY_DAY_OFF_INFO_MAP = null;
        }

        Map<String, CompanyDayOffInfo> map = new HashMap<>();

        for (String companyId : COMPANY_MAP.keySet()) {

            List<BuzConfig> configs = buzConfigRepository.findByCompanyIdAndSection(companyId, Constant.DAY_OFF_INFO);
            if (configs.isEmpty()) {
                throw new Exception(String.format("Find BuzConfig for company [%s] return empty", companyId));
            }
            CompanyDayOffInfo info = new CompanyDayOffInfo();

            boolean allowCancelRequestValue = Boolean.parseBoolean(findByKey(configs, Constant.ALLOW_CANCEL_REQUEST));
            int maxDayAllowCancelValue  = Integer.parseInt(findByKey(configs, Constant.MAX_DAY_ALLOW_CANCEL));
            List<CompanyDayOffInfo.CategoryList> categoryListValue =
                    StringUtil.json2Collection(findByKey(configs, Constant.CATEGORY_LIST),
                            new TypeToken<List<CompanyDayOffInfo.CategoryList>>() {}.getType());
            Map<Float, Float> dayOffByLevelValue =
                    StringUtil.json2Collection(findByKey(configs, Constant.DAY_OFF_BY_LEVEL), new TypeToken<Map<Float, Float>>() {}.getType());

            info.setAllowCancelRequest(allowCancelRequestValue);
            info.setMaxDayAllowCancel(maxDayAllowCancelValue);
            info.setCategoryList(categoryListValue);
            info.setDayOffByLevel(dayOffByLevelValue);

            map.put(companyId, info);
        }

        COMPANY_DAY_OFF_INFO_MAP = hazelcastService.reload(map, CacheKeyEnum.COMPANY_DAY_OFF);
        log.info("[loadCompanyDayOffInfoMap] COMPANY_DAY_OFF_INFO_MAP loaded succeed [{}]", StringUtil.toJsonString(COMPANY_DAY_OFF_INFO_MAP));
    }

    public void loadBuzConfigMap() {
    }

    private String findByKey(List<BuzConfig> list, String key) {
        for (BuzConfig config : list) {
            if (config.getKey().equals(key)) {
                return config.getValue();
            }
        }

        return StringUtil.EMPTY;
    }

}
