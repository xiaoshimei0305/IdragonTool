package store.idragon.tool.base.dto.result;

import lombok.Data;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.exception.IDragonException;

/**
 * @author xiaoshimei0305
 * date  2020/12/12 10:21 下午
 * description 统一REST 接口返回结果
 * @version 1.0
 */
@Data
public class ResultInfo<T> {
    /**
     * 结果编码【全0标示操作成功，其他标示操作失败】
     */
    private String code;
    /**
     * 结果消息
     */
    private String message;

    /**
     * 结果数据
     */
    private T data;

    /**
     * 无参构造方法
     */
    public ResultInfo() {
    }

    /**
     * 构建结果数据
     * @param data 数据
     * @param iCodeMessage  编码消息
     */
    public ResultInfo(T data, ICodeMessage iCodeMessage) {
        this.data=data;
        initCodeMessage(iCodeMessage);
    }

    /**
     * 获取异常错误信息返回给前台
     * @param exception 异常信息
     * @return 错误描述信息
     */
    public static   ResultInfo<String> fail(Exception exception){
        IDragonException iDragonException;
        if(exception instanceof IDragonException){
            iDragonException=(IDragonException) exception;
        }else{
            iDragonException=new IDragonException(exception);
        }
        return fail(iDragonException);
    }

    /**
     * 获取异常错误信息返回给前台
     * @param exception 异常信息
     * @return 错误描述信息
     */
    public static   ResultInfo<String> fail(IDragonException exception){
        ResultInfo failInfo=new ResultInfo();
        failInfo.setMessage(exception.getMessage());
        failInfo.setCode(exception.getCode());
        failInfo.setData(exception.getLocalizedMessage());
        return failInfo;
    }
    /**
     * 返回失败消息
     * @param <S> 数据类型
     * @return 错误结果
     */
    public static <S>  ResultInfo<S> fail(){
        return new ResultInfo<S>(null, BaseResultEnum.FAILED);
    }

    /**
     * 返回成功消息
     * @param data 成功数据
     * @param <S> 数据类型
     * @return 成功结果
     */
    public static <S>  ResultInfo<S> ok(S data){
        return new ResultInfo<S>(data, BaseResultEnum.SUCCESS);
    }
    /**
     * 返回成功消息【用户阅读消息】
     * @param data 成功数据
     * @param <S> 数据类型
     * @return 成功结果
     */
    public static <S>  ResultInfo<S> okWithoutMsg(S data){
        return new ResultInfo<S>(data, BaseResultEnum.SUCCESS_NO_MESSAGE);
    }

    /**
     * 初始化编码/名称
     * @param iCodeMessage 数据提供载体
     */
    private  void initCodeMessage(ICodeMessage iCodeMessage){
        if(iCodeMessage!=null){
            this.code=iCodeMessage.getCode();
            this.message=iCodeMessage.getMessage();
        }
    }

    /**
     * 判断请求结果
     * @return 成功标示
     */
    public boolean isSuccess() {
        return StringUtils.isBlank(this.code.replace("0",""));
    }
}
