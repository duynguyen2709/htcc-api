package htcc.common.constant;

public enum CacheKeyEnum {

    COMPANY,
    OFFICE,
    DEPARTMENT,
    BUZ_CONFIG,
    COMPANY_DAY_OFF,
    WORKING_DAY,
    ALL;

    public static CacheKeyEnum getKey(String type){
        for (CacheKeyEnum key : CacheKeyEnum.values()){
            if (key.name().equalsIgnoreCase(type)) {
                return key;
            }
        }

        return null;
    }
}
