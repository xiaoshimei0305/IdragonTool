package store.idragon.tool.http.wechat;

import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.check.ParamCheckUtils;
import store.idragon.tool.base.inf.IKeyValueHolder;

/**
 * @author xiaoshimei0305
 * date  2020/12/27 12:00 上午
 * description
 * @version 1.0
 */
public class SessionKeyHolder implements  ISessionKeyHolder {
    /**
     * 存储session key值
     */
    private String key;
    /**
     * 数据存储工具
     */
    private IKeyValueHolder<String> keyValueHolder;

    /**
     * 默认数据存储工具创建
     * @param keyValueHolder 数据持有对象
     */
    public SessionKeyHolder(IKeyValueHolder<String> keyValueHolder) {
        this(null,keyValueHolder);
    }

    /**
     * 自定义存储key工具
     * @param key 存储key，默认session_key
     * @param keyValueHolder  数据存储对象
     */
    public SessionKeyHolder(String key, IKeyValueHolder<String> keyValueHolder) {
        this.key = key;
        if(StringUtils.isBlank(this.key)){
            this.key="session_key";
        }
        ParamCheckUtils.notNull(keyValueHolder,"SessionKeyHolder创建时，IKeyValueHolder对象");
        this.keyValueHolder = keyValueHolder;
    }

    @Override
    public String getSessionKey() {
        return keyValueHolder.get(key);
    }

    @Override
    public void storeSessionKey(String sessionKey) {
        keyValueHolder.store(key,sessionKey);
    }
}
