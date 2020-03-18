package htcc.common.entity.base;

import java.util.Map;

public interface LogEntity {

    public Map<String, Object> getHashMap();

    public String getSQLCreateTableSchemaScript();

    public long getCreateTime();

    public String getTableName();

    public String tableName = "";

    public long createTime = 0;
}
