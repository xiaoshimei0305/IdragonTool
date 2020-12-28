package store.idragon.tool.rest;

import com.alibaba.fastjson.JSONObject;
import store.idragon.tool.base.dto.result.ResultInfo;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author xiaoshimei0305
 * date  2020/12/27 1:07 下午
 * <p>向前端返回信息</p>
 * @version 1.0
 */
public class ResponseUtils {
    /**
     * 返回前端数据
     * @param data 数据
     * @param response 响应工具
     * @param <T> 数据类型
     */
    public static <T>  void responseData(T data, HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter writer = response.getWriter();
        writer.write(JSONObject.toJSONString(data));
        writer.flush();
    }

    /**
     * 异常情况下返回结果
     * @param exception 异常信息
     * @param response 响应工具
     */
    public static void responseWhenException(Exception exception, HttpServletResponse response) throws IOException {
        responseData(ResultInfo.fail(exception),response);
    }


}
