package store.idragon.tool.base.dto.result;

/**
 * @author xiaoshimei0305
 * date  2020/12/13 12:57 上午
 * <p>定义基本成功/失败美剧</p>
 * @version 1.0
 */
public enum BaseResultEnum implements ICodeMessage{
    /**
     * 操作成功
     */
    SUCCESS("00000000","操作成功"),
    /**
     * 操作成功【无消息】
     */
    SUCCESS_NO_MESSAGE("00000000",""),
    /**
     * 参数错误
     */
    FAILED_PARAMS_EMPTY_CHECK("00000001","参数:%s不能为空"),
    /**
     * 操作失败
     */
    FAILED("11111111","操作失败");
    private String code;
    private String message;
    private BaseResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 获取编码
     * @return 编码
     */
    @Override
    public String getCode() {
        return code;
    }

    /**
     * 获取描述信息
     * @return 描述信息
     */
    @Override
    public String getMessage() {
        return message;
    }
}
