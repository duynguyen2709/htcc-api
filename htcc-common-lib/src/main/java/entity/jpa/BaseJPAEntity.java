package entity.jpa;

import java.io.Serializable;

public abstract class BaseJPAEntity implements Serializable {
    public abstract boolean isValid();
}
