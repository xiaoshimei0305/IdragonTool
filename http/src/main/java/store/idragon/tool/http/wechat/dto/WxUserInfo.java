package store.idragon.tool.http.wechat.dto;

import lombok.Data;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 9:02 下午
 * <p>获取微信用户信息过程中获取到到用户信息</p>
 * @version 1.0
 */
@Data
public class WxUserInfo extends  LoginInfo {
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 性别
     */
    private String gender;
    /**
     * 城市
     */
    private String city;
    /**
     * 省份
     */
    private String province;
    /**
     * 国家
     */
    private String country;
    /**
     * 头像
     */
    private String avatarUrl;

}
