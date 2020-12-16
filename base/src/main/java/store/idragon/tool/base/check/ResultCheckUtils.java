package store.idragon.tool.base.check;

import com.alibaba.fastjson.JSONObject;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.dto.result.ICodeMessage;
import store.idragon.tool.base.exception.IDragonException;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 4:53 下午
 * <p>检查结果数据</p>
 * @version 1.0
 */
public class ResultCheckUtils {
    /**
     * 检查请求结果数据
     * @param data 业务返回结果
     */
    public static void checkJson(JSONObject data){
        checkJson(data,null,null);
    }

    /**
     * 检查请求结果数据
     * @param data 业务返回结果
     * @param codeKey code内容在data中的key
     * @param messageKey message内容在data中的key
     */
    public static void checkJson(JSONObject data,String codeKey,String messageKey){
        ParamCheckUtils.notNull(data,"结果数据不存在");
        String code= StringUtils.getValue(codeKey,"code");
        String message = StringUtils.getValue(messageKey,"message");
        String codeValue =data.getString(code);
        String messageValue = data.getString(message);
        // 判断编码非0则是异常
        if(StringUtils.isNotBlank(codeValue)&&!StringUtils.isBlank(codeValue.replace("0",""))){
            ICodeMessage codeMessage=new ICodeMessage() {
                @Override
                public String getCode() {
                    return codeValue;
                }

                @Override
                public String getMessage() {
                    return messageValue;
                }
            };
            throw new IDragonException(codeMessage);
        }
    }
}
