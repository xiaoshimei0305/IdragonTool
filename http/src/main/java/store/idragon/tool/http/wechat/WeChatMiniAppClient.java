package store.idragon.tool.http.wechat;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.check.ParamCheckUtils;
import store.idragon.tool.base.check.ResultCheckUtils;
import store.idragon.tool.base.exception.IDragonException;
import store.idragon.tool.http.HttpRequestUtils;
import store.idragon.tool.http.wechat.dto.LoginInfo;
import store.idragon.tool.http.wechat.dto.WxPhoneInfo;
import store.idragon.tool.http.wechat.dto.WxUserInfo;

import java.io.IOException;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 3:51 下午
 * description 服务端微信请求统一工具
 * @version 1.0
 */
@Slf4j
public class WeChatMiniAppClient {
    /**
     * 客户端appId
     */
    private String appId;
    /**
     *  appSecret
     */
    private String appSecret;
    /**
     * 用于持有微信端sessionKey对象
     */
    private ISessionKeyHolder sessionKeyHolder;

    /**
     * 微信客户端创建
     * @param appId  微信ID
     * @param appSecret 微信密钥
     * @param sessionKeyHolder 保存sessionKey
     */
    public WeChatMiniAppClient(String appId, String appSecret,ISessionKeyHolder sessionKeyHolder) {
        ParamCheckUtils.notNull(appId,"appId");
        ParamCheckUtils.notNull(appSecret,"密文");
        ParamCheckUtils.notNull(sessionKeyHolder,"sessionKey保持对象");
        this.appId = appId;
        this.appSecret = appSecret;
        this.sessionKeyHolder =sessionKeyHolder;
    }

    /**
     * 通过前端code换取 openId, unionId
     * @param code 票据
     * @return  用户openId信息
     */
    public LoginInfo getLoginInfo(String code){
        ParamCheckUtils.notNull(code,"票据");
        StringBuffer urlBuffer=new StringBuffer("https://api.weixin.qq.com/sns/jscode2session?appid=");
        urlBuffer.append(appId).append("&secret=");
        urlBuffer.append(appSecret).append("&js_code=");
        urlBuffer.append(code).append("&grant_type=authorization_code");
        try {
            JSONObject resultData = HttpRequestUtils.getJson(urlBuffer.toString());
            ResultCheckUtils.checkJson(resultData,"errcode","errmsg");
            //存储sessionKey
            sessionKeyHolder.storeSessionKey(resultData.getString("session_key"));
            return resultData.toJavaObject(LoginInfo.class);
        }catch (IOException e){
           log.error("获取小程序登录后信息：{}",e);
            throw new IDragonException(e);
        }
    }

    /**
     * 获取用户完整信息
     * @param encryptedData 加密内容
     * @param iv 偏移量
     * @return 用户信息
     */
    public WxUserInfo getUserInfo(String encryptedData, String iv){
        return parseWxInfo(encryptedData,iv).toJavaObject(WxUserInfo.class);
    }

    /**
     * 获取用户手机信息
     * @param encryptedData 加密内容
     * @param iv 偏移量
     * @return 手机号码相关信息
     */
    public WxPhoneInfo getPhoneInfo(String encryptedData, String iv){
        return parseWxInfo(encryptedData,iv).toJavaObject(WxPhoneInfo.class);
    }

    /**
     * 解析微信信息
     * @param encryptedData 加密内容【微信端传入】
     * @param iv     偏移量
     * @return 解析内容
     */
    private JSONObject parseWxInfo(String encryptedData, String iv){
        try {
            JSONObject resultData=JSONObject.parseObject(StringUtils.aesDecrypt(encryptedData,iv,sessionKeyHolder.getSessionKey()));
            ResultCheckUtils.checkJson(resultData,"errcode","errmsg");
            return resultData;
        }catch (Exception e){
            log.error("解析微信用户信息异常：{}",e);
            throw  new IDragonException(e);
        }
    }


}
