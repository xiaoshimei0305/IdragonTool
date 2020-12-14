package store.idragon.tool.base;

/**
 * @author xiaoshimei0305
 * date  2020/12/13 12:57 上午
 * description
 * @version 1.0
 */
public enum ResultTypeEnum {
    SUCCESS("00000000","操作成功"),SUCCESS_NO_MESSAGE("0000",""),
    FAILED("11111111","操作失败");
    private String code;
    private String message;
    ResultTypeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
