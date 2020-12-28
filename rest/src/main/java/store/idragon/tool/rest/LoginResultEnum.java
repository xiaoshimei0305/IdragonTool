package store.idragon.tool.rest;

import store.idragon.tool.base.dto.result.ICodeMessage;

/**
 * @author xiaoshimei0305
 * date  2020/12/27 1:54 下午
 * description
 * @version 1.0
 */
public enum LoginResultEnum implements ICodeMessage {
    /**
     * 登录验证错误
     */
    FAILED_LOGIN_CHECK("01000001","登录验证错误：%s");
    private String code;
    private String message;

    LoginResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
