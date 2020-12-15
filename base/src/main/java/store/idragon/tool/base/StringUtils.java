package store.idragon.tool.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author xiaoshimei0305
 * @version 1.0
 * 2020/10/18 10:53 下午
 * {@inheritDoc}
 * <p>常用字符串处理工具,通过继承{@link org.apache.commons.lang3.StringUtils}引入apache常用工具【该工具在得到广大开发成员使用，简化重复劳动】。</p>
 * @see org.apache.commons.lang3.StringUtils
 */
public class StringUtils extends  org.apache.commons.lang3.StringUtils  {

    /**
     * 获取字数据【提供默认值】
     * @param value 数据
     * @param defaultValue  数据为空时默认值
     * @return 返回获取到的数据
     */
    public static String getValue(String value,String defaultValue){
        return  isBlank(value)? defaultValue:value;
    }

    /**
     * 美化json字符串
     * @param jsonString json格式问题
     * @return 格式化JSON数据，便于阅读
     */
    public static String toPrettyJsonString(String jsonString){
        if(!isBlank(jsonString)){
            JSONObject json = JSONObject.parseObject(jsonString);
            return JSONObject.toJSONString(json, SerializerFeature.PrettyFormat, SerializerFeature.WriteMapNullValue,
                    SerializerFeature.WriteDateUseDateFormat);
        }
        return jsonString;
    }
}
