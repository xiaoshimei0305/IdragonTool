package store.idragon.tool.base.dto.result;

import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.check.ParamCheckUtils;

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

    /**
     * 获取错误消息编码全量信息【主要用于异常信息直接收消息情况】
     * @param  args 格式化参数
     * @return 返回全量错误信息
     */
    default String getCodeMessage(String... args){
        String code=getCode();
        String message= StringUtils.getValue(detailMessage(args),"未知错误");
        ParamCheckUtils.notNull(code,"错误编码");
        return String.format("%s,[code:%s]",message,code);
    }

    /**
     * 获取完整错误信息
     * @param args 格式化数据
     * @return 返回错误详细
     */
    default String detailMessage(String... args){
        return String.format(getMessage(),args);
    }

}
