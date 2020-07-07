package htcc.common.constant;

public enum CacheKeyEnum {

    // Employee
    COMPANY,
    OFFICE,
    DEPARTMENT,
    BUZ_CONFIG,
    COMPANY_DAY_OFF,
    WORKING_DAY,
    SHIFT_TIME,
    FIXED_SHIFT,
    SHIFT_TEMPLATE,

    // Admin
    NOTI_ICON,
    SUPPORTED_FEATURE,
    FEATURE_COMBO,
    CUSTOMER_ORDER,
    ;
//    ALL;

    public static CacheKeyEnum getKey(String type){
        for (CacheKeyEnum key : CacheKeyEnum.values()){
            if (key.name().equalsIgnoreCase(type)) {
                return key;
            }
        }

        return null;
    }
}
