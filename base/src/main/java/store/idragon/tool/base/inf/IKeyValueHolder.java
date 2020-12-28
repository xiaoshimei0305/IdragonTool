package store.idragon.tool.base.inf;

/**
 * @author xiaoshimei0305
 * date  2020/12/26 11:50 下午
 * <p>key-value 数据存储对象</p>
 * @version 1.0
 */
public interface IKeyValueHolder<V> {
    /**
     * 存储数据
     * @param key 存储key
     * @param value 值
     */
    void store(String key,V value);

    /**
     * 获取存储数据
     * @param key 存储时key值
     * @return 存储数据
     */
    V get(String key);

}
