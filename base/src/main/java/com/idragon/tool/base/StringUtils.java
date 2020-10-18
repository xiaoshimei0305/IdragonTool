package com.idragon.tool.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * @author xiaoshimei0305
 * @version 1.0
 * @date 2020/10/18 10:53 下午
 * @description 字符串处理工具
 */
public class StringUtils extends  org.apache.commons.lang3.StringUtils  {

    /**
     * 通过指定字符串内容，如果内容为空，返回
     * @param value
     * @param defaultValue
     * @return
     */
    public static String getValue(String value,String defaultValue){
        if(!isBlank(value)){
            return value;
        }
        return defaultValue;
    }

    /**
     * 美化json字符串
     * @param jsonString json格式字符串
     * @return
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
