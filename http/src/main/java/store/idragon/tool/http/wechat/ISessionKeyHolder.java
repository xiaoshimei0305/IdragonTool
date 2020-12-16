package store.idragon.tool.http.wechat;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 9:10 下午
 * description
 * @version 1.0
 */
public interface ISessionKeyHolder {
    /**
     * sessionKey 获取接口
     * @return 当前有效的sessionKey
     */
    public String getSessionKey();

    /**
     * sessionKey存储，保障应用统一
     * @param sessionKey 登录之后获取到到sessionKey
     */
    public void storeSessionKey(String sessionKey);
}
