package service;

import java.util.List;

public abstract class BaseJPAService<T, K>  {
    public abstract  List<T> findAll();
    public abstract T findById(K key);
    public abstract T create(T t);
    public abstract T update(T t);
    public abstract void delete(K key);

}
