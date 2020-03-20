package htcc.common.entity.base;

import htcc.common.component.LoggingConfiguration;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Map;

public abstract class BaseLogEntity implements Serializable {

    private static final long serialVersionUID = 192632583005150807L;

    @Transient
    public abstract Map<String, Object> getParamsMap();

    public abstract long getCreateTime();

    public abstract String retrieveTableName();

    public String requestId = LoggingConfiguration.getTraceId();
}
