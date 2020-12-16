package store.idragon.tool.http.wechat.dto;

import lombok.Data;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 3:53 下午
 * <p>结果请参考 https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/login/auth.code2Session.html</p>
 * @version 1.0
 */
@Data
public class LoginInfo {
    /**
     * 用户open
     */
    private String openId;
    /**
     * unionId 【存在为空情况】
     */
    private String unionId;
}
