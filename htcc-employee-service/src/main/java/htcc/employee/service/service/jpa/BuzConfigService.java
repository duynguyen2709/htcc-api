package htcc.employee.service.service.jpa;

import htcc.common.constant.Constant;
import htcc.common.entity.dayoff.CompanyDayOffInfo;
import htcc.common.entity.jpa.BuzConfig;
import htcc.common.entity.jpa.Company;
import htcc.common.service.BaseJPAService;
import htcc.common.util.StringUtil;
import htcc.employee.service.component.hazelcast.HazelcastLoader;
import htcc.employee.service.config.DbStaticConfigMap;
import htcc.employee.service.repository.jpa.BuzConfigRepository;
import htcc.employee.service.repository.jpa.CompanyRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Log4j2
public class BuzConfigService extends BaseJPAService<BuzConfig, BuzConfig.Key> {

    private static final String DEFAULT_DAY_OFF_CATEGORY = "Nghỉ phép năm";

    @Autowired
    private BuzConfigRepository repo;

    @Autowired
    private HazelcastLoader hazelcastLoader;

    @Override
    public List<BuzConfig> findAll() {
        return new ArrayList<>(DbStaticConfigMap.BUZ_CONFIG_MAP.values());
    }

    @Override
    public BuzConfig findById(BuzConfig.Key key) {
        String rawKey = String.format("%s_%s_%s", key.getCompanyId(), key.getSection(), key.getKey());
        return DbStaticConfigMap.BUZ_CONFIG_MAP.getOrDefault(rawKey, null);
    }

    @Override
    public BuzConfig create(BuzConfig config) {
        BuzConfig newCompany = repo.save(config);
        hazelcastLoader.loadBuzConfigMap();
        return config;
    }

    @Override
    public BuzConfig update(BuzConfig config) {
        return create(config);
    }

    @Override
    public void delete(BuzConfig.Key key) {
        repo.deleteById(key);
        hazelcastLoader.loadBuzConfigMap();
    }

    public List<BuzConfig> createDefaultDayOffInfo(String companyId) throws Exception {
        List<BuzConfig> list = new ArrayList<>();
        CompanyDayOffInfo info = new CompanyDayOffInfo();

        info.getCategoryList().add(new CompanyDayOffInfo.CategoryList(DEFAULT_DAY_OFF_CATEGORY, true, true));
        info.getDayOffByLevel().put(0.0f, 10.0f);
        info.setAllowCancelRequest(true);
        info.setMaxDayAllowCancel(0);

        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.ALLOW_CANCEL_REQUEST, info.isAllowCancelRequest() + ""));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.MAX_DAY_ALLOW_CANCEL, info.getMaxDayAllowCancel() + ""));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.CATEGORY_LIST, StringUtil.toJsonString(info.getCategoryList())));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.DAY_OFF_BY_LEVEL, StringUtil.toJsonString(info.getDayOffByLevel())));

        list = repo.saveAll(list);
        hazelcastLoader.loadBuzConfigMap();
        hazelcastLoader.loadCompanyDayOffInfoMap();
        return list;
    }

    public CompanyDayOffInfo getDayOffInfo(String companyId){
        return DbStaticConfigMap.COMPANY_DAY_OFF_INFO_MAP.getOrDefault(companyId, null);
    }

    public CompanyDayOffInfo updateDayOffInfo(String companyId, CompanyDayOffInfo request) throws Exception {
        List<BuzConfig> list = new ArrayList<>();

        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.ALLOW_CANCEL_REQUEST, request.isAllowCancelRequest() + ""));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.MAX_DAY_ALLOW_CANCEL, request.getMaxDayAllowCancel() + ""));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.CATEGORY_LIST, StringUtil.toJsonString(request.getCategoryList())));
        list.add(new BuzConfig(companyId, Constant.DAY_OFF_INFO, Constant.DAY_OFF_BY_LEVEL, StringUtil.toJsonString(request.getDayOffByLevel())));

        list = repo.saveAll(list);
        hazelcastLoader.loadBuzConfigMap();
        hazelcastLoader.loadCompanyDayOffInfoMap();

        return getDayOffInfo(companyId);
    }
}
