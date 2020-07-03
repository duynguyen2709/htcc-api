package htcc.admin.service.config;

import htcc.common.entity.feature.FeatureCombo;
import htcc.common.entity.feature.FeaturePrice;
import htcc.common.entity.icon.NotificationIconConfig;

import java.util.HashMap;
import java.util.Map;

public class DbStaticConfigMap {

    public static Map<String, NotificationIconConfig> NOTI_ICON_MAP = new HashMap<>();
    public static Map<String, FeaturePrice> SUPPORTED_FEATURE_MAP = new HashMap<>();
    public static Map<String, FeatureCombo> FEATURE_COMBO_MAP = new HashMap<>();
}
