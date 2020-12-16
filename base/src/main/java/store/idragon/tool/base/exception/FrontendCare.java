package store.idragon.tool.base.exception;

import store.idragon.tool.base.dto.result.ICodeMessage;

/**
 * 前端关心异常信息【主要提供前端用户提示】
 * @author xiaoshimei0305
 * date  2020/12/15 3:04 下午
 * description
 * @version 1.0
 */
public class FrontendCare  implements ICodeMessage {
    /**
     * 编码
     */
    private String code;
    /**
     * 消息
     */
    private String message;

    /**
     * 通过{ @see ICodeMessage }创建前端需要的错误编码信息
     * @param codeMessage 错误编码信息
     * @param args 错误信息占位符参数
     */
    public FrontendCare(ICodeMessage codeMessage,String... args) {
        this.code=codeMessage.getCode();
        this.message=codeMessage.detailMessage(args);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
