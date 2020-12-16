package store.idragon.tool.base.check;

import lombok.extern.slf4j.Slf4j;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.base.dto.result.BaseResultEnum;
import store.idragon.tool.base.exception.IDragonException;

/**
 * @author xiaoshimei0305
 * date  2020/12/16 4:43 下午
 * <p>参数检查工具</p>
 * @version 1.0
 */
@Slf4j
public class ParamCheckUtils {
    /**
     * 检查参数是否为空
     * @param data 验证内容
     * @param message 错误提示
     */
    public static void notNull(Object data,String message){
        if(data == null){
            throw new IDragonException(BaseResultEnum.FAILED_PARAMS_EMPTY_CHECK,message);
        }
        if(data instanceof  String){
            String value=(String) data;
            if(StringUtils.isBlank(value)){
                throw new IDragonException(BaseResultEnum.FAILED_PARAMS_EMPTY_CHECK,message);
            }
        }
    }

}
