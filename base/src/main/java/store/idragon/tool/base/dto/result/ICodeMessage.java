package store.idragon.tool.base.dto.result;

/**
 * @author xiaoshimei0305
 * date  2020/12/14 10:36 下午
 * <p>标记一类编码/消息 信息结果。主要用于一方面提供程序方便识别编码，一方面提供用户阅读消息</p>
 * @version 1.0
 */
public interface ICodeMessage {
    /**
     * 获取机器识别编码
     * @return 编码
     */
    public String getCode();

    /**
     * 获取用户识别消息
     * @return 消息
     */
    public String getMessage();

}
