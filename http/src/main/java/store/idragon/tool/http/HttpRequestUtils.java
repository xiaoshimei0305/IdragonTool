package store.idragon.tool.http;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import store.idragon.tool.base.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 常用HTTP请求方法
 * @author xiaoshimei0305
 * @version 1.0
 * date 2020/10/18 11:04 下午
 * <p>汇集常用HTTP请求方法【极简模式】，遇到复杂模式请使用{@link HttpClients}。</p>
 */
@Slf4j
public class HttpRequestUtils {
    /**
     * 获取HTTP响应体文本内容
     * @param response 响应实体
     * @return 响应内容
     * @throws UnsupportedOperationException 工具未支持的请求
     * @throws IOException 网络访问异常
     */
    public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException{
        return getHttpEntityContent(response,null);
    }
    /**
     * 获取HTTP响应体文本内容
     * @param response 响应实体
     * @param charSet 字符集，默认utf-8
     * @return 响应内容
     * @throws UnsupportedOperationException 工具未支持的请求
     * @throws IOException 网络访问异常
     */
    public static String getHttpEntityContent(HttpResponse response,String charSet) throws UnsupportedOperationException, IOException {
        StringBuffer sb=new StringBuffer();
        HttpEntity entity = response.getEntity();
        if(entity != null){
            InputStream in = entity.getContent();
            BufferedReader br = new BufferedReader(new InputStreamReader(in, StringUtils.getValue(charSet,"utf-8")));
            String line = null;
            while((line = br.readLine())!=null){
                sb.append(line).append("\n");
            }
            br.close();
            in.close();
        }
        return sb.toString();
    }
    /**
     * GET请求获取数据
     * @param url 请求地址
     * @param charset  字符集
     * @return 请求结果
     * @throws IOException 网络访问异常
     */
    public static String get(String url,String charset) throws IOException {
        log.info("网络请求地址：{}",url);
        HttpGet get = new HttpGet(url);
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpResponse response = httpClient.execute(get);
            return  getHttpEntityContent(response,charset);
        } finally{
            get.abort();
        }
    }
    /**
     * GET请求获取数据
     * @param url 请求地址
     * @return 请求结果
     * @throws IOException 网络访问异常
     */
    public static String get(String url) throws IOException {
        return get(url,null);
    }
    /**
     * 获取JSON格式内容
     * @param url 请求地址
     * @return JSON格式结果，存在为空情况
     * @throws IOException 网络访问异常
     */
    public static JSONObject getJson(String url)  throws IOException{
        return getJson(url,null);
    }
    /**
     * 获取JSON格式内容
     * @param url 请求地址
     * @param charSet 内容编码
     * @return JSON格式结果，存在为空情况
     * @throws IOException 网络访问异常
     */
    public static JSONObject getJson(String url, String charSet)  throws IOException{
        return JSONObject.parseObject(StringUtils.getValue(get(url,charSet),"{}"));
    }

}
