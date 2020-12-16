package store.idragon.tool.http.wechat.dto;

import lombok.Data;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 9:23 下午
 * <p>微信获取的用户信息</p>
 * @version 1.0
 */
@Data
public class WxPhoneInfo {
    /**
     * 用户绑定的手机号（国外手机号会有区号
     */
    private String phoneNumber;
    /**
     * 没有区号的手机号
     */
    private String purePhoneNumber;
    /**
     * 区号
     */
    private String countryCode;

}
