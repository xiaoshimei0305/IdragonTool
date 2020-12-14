package store.idragon.tool.base.dto;

import lombok.Data;
import store.idragon.tool.base.ResultTypeEnum;

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
     * @param data
     * @param type
     */
    public ResultInfo(T data,ResultTypeEnum type) {
        this.data=data;
        initCodeMessage(type);
    }

    /**
     * 返回失败消息
     * @param <S>
     * @return
     */
    public static <S>  ResultInfo<S> fail(){
        return new ResultInfo<S>(null,ResultTypeEnum.FAILED);
    }

    /**
     * 返回成功消息
     * @param data 成功结果数据
     * @param <S>
     * @return
     */
    public static <S>  ResultInfo<S> ok(S data){
        return new ResultInfo<S>(data,ResultTypeEnum.SUCCESS);
    }
    /**
     * 返回成功消息
     * @param data 成功结果数据
     * @param <S>
     * @return
     */
    public static <S>  ResultInfo<S> okWithoutMsg(S data){
        return new ResultInfo<S>(data,ResultTypeEnum.SUCCESS_NO_MESSAGE);
    }

    /**
     * 初始化编码/名称
     * @param type
     */
    private  void initCodeMessage(ResultTypeEnum type){
        if(type!=null){
            this.code=type.getCode();
            this.message=type.getMessage();
        }
    }

}
