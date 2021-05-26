package store.idragon.tool.rest;

import store.idragon.tool.base.inf.IKeyValueHolder;

/**
 * @author xiaoshimei0305
 * date  2020/12/26 11:45 下午
 * <p>统一令牌管理</p>
 * @version 1.0
 */
public class TokenManager<T> {
    /**
     * 存储数据对象
     */
    private IKeyValueHolder<T> dataHolder;

    /**
     * 创建用户存储对象
     * @param dataHolder 存储数据对象
     */
    public TokenManager(IKeyValueHolder<T> dataHolder) {
        this.dataHolder = dataHolder;
    }

    /**
     *  存储用户登录信息
     * @param key 登录key
     * @param data 数据
     */
    public void storeToken(String key, T data){
        dataHolder.store(key,data);
    }

    /**
     * 验证用户登录状况
     * @param key 前端传入token值
     * @return 用户登录信息
     */
    public T validToken(String key){
        return dataHolder.get(key);
    }

}
