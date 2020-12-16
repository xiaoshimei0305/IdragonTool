package store.idragon.tool.base.exception;

import store.idragon.tool.base.dto.result.ICodeMessage;

/**
 * 后端关心异常信息
 * @author xiaoshimei0305
 * date  2020/12/15 3:04 下午
 * description
 * @version 1.0
 */
public class BackendCare implements ICodeMessage {
    /**
     * 编码
     */
    private String code;
    /**
     * 消息
     */
    private String message;
    /**
     * 错误异常堆栈信息
     */
    private Throwable cause;

    /**
     * 通过{ @see ICodeMessage } { @see Throwable}创建后端需要的错误编码信息
     * @param cause 异常堆栈信息
     * @param codeMessage 错误编码信息
     */
    public BackendCare(Throwable cause,ICodeMessage codeMessage) {
        this(codeMessage);
        this.cause = cause;
    }

    /**
     * 通过{ @see ICodeMessage } 创建后端需要的错误编码信息
     * @param codeMessage  错误编码信息
     */
    public BackendCare(ICodeMessage codeMessage) {
        this.code=codeMessage.getCode();
        this.message=codeMessage.getMessage();
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    /**
     * 获取异常堆栈信息
     * @return 返回堆栈信息对象
     */
    public Throwable getCause() {
        return cause;
    }

    /**
     * 异常消息个性化设置
     * @param message 消息内容
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
