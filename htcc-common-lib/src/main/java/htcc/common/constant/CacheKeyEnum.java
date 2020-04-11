package htcc.common.constant;

public enum CacheKeyEnum {

    COMPANY,
    BUZ_CONFIG,
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
