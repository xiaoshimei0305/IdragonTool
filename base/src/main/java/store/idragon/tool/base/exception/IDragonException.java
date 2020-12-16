package store.idragon.tool.base.exception;

import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.dto.result.BaseResultEnum;
import store.idragon.tool.base.dto.result.ICodeMessage;

/**
 * 通用运行时异常
 * @author xiaoshimei0305
 * date  2020/12/15 2:53 下午
 * description
 * @version 1.0
 */
public class IDragonException extends  RuntimeException  implements ICodeMessage{
    /**
     * 前端关心的异常信息
     */
    private BackendCare backendCare;
    /**
     * 后端关心的异常信息
     */
    private FrontendCare frontendCare;

    /**
     * 默认异常信息
     * @param args 错误信息占位符参数
     */
    public IDragonException(String... args) {
        this(BaseResultEnum.FAILED,args);
    }

    /**
     * 通过异常直接创建【主要用于其他异常封装】
     * @param codeMessage  错误信息
     * @param args 错误信息占位符参数
     */
    public IDragonException(ICodeMessage codeMessage,String... args) {
        super(codeMessage.getCodeMessage(args));
        initCodeMessage(codeMessage,args);
    }
    /**
     * 通过异常直接创建【主要用于其他异常封装】
     * @param exception 异常
     */
    public IDragonException(Exception exception) {
       this(exception,null);
    }
    /**
     *  异常创建
     * @param exception 异常信息
     * @param codeMessage 注入异常编码/消息内容
     * @param args 错误信息占位符参数
     *  <p>异常信息直接展示后端异常信息，然后根据{ @see mapCodeMessageForFront}进行编码转化【默认不进行任何调整】</p>
     */
    public IDragonException(Exception exception,ICodeMessage codeMessage,String... args) {
        super(exception.getMessage(), exception.getCause(), true, false);
        if(codeMessage==null){
            if(exception instanceof  ICodeMessage ){
                codeMessage=(ICodeMessage) exception;
            }else{
                codeMessage= BaseResultEnum.FAILED;
            }
        }
        initCodeMessage(codeMessage,args);
        /**
         * 默认失败情况下，把错误异常信息设置到错误消息中。其他情况以codeMessage传入为准
         */
        String exceptionMessage=exception.getMessage();
        if(codeMessage==BaseResultEnum.FAILED && StringUtils.isNotBlank(exceptionMessage)){
            this.backendCare.setMessage(exceptionMessage);
        }
    }

    /**
     * 初始化编码消息信息
     * @param codeMessage 编码消息信息
     * @param args 错误信息占位符参数
     */
    private void initCodeMessage(ICodeMessage codeMessage,String... args){
        if(codeMessage==null){
            codeMessage=BaseResultEnum.FAILED;
        }
        this.backendCare=new BackendCare(this.getCause(),codeMessage,args);

        this.frontendCare=new FrontendCare(mapCodeMessageForFront(),args);
    }
    @Override
    public String getCode() {
        return backendCare.getCode();
    }

    /**
     * 前端编码展示转换【默认直接展示后端消息】
     * @return 前端展示内容
     */
    protected ICodeMessage mapCodeMessageForFront(){
        return this;
    }



}
