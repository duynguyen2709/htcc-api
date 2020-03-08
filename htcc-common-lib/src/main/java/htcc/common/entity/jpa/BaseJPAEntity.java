package htcc.common.entity.jpa;

import java.beans.Transient;
import java.io.Serializable;

public abstract class BaseJPAEntity implements Serializable {

    @Transient
    public abstract boolean isValid();
}
