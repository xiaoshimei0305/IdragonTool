package store.idragon.tool.http;

import com.alibaba.fastjson.JSONObject;
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
 * 常用网络请求接口，适用于简单请求模式。如果需要复杂配置，请使用apache API进行请求
 * @author xiaoshimei0305
 * @version 1.0
 * date 2020/10/18 11:04 下午
 * description
 */
public class HttpRequestUtils {
    /**
     * 获取HTTP响应体文本内容
     * @param response 响应实体
     * @return 响应内容
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    public static String getHttpEntityContent(HttpResponse response) throws UnsupportedOperationException, IOException{
        return getHttpEntityContent(response,null);
    }
    /**
     * 获取HTTP响应体文本内容
     * @param response 响应实体
     * @param charSet 字符集，默认utf-8
     * @return 响应内容
     * @throws UnsupportedOperationException
     * @throws IOException
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
     * @return 请求结果
     * @throws IOException
     */
    public static String get(String url,String charset) throws IOException {
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
     * @throws IOException
     */
    public static String get(String url) throws IOException {
        return get(url,null);
    }
    /**
     * 获取JSON格式内容
     * @param url 请求地址
     * @return JSON格式结果，存在为空情况
     * @throws IOException
     */
    public static JSONObject getJson(String url)  throws IOException{
        return getJson(url,null);
    }
    /**
     * 获取JSON格式内容
     * @param url 请求地址
     * @param charSet 内容编码
     * @return JSON格式结果，存在为空情况
     * @throws IOException
     */
    public static JSONObject getJson(String url, String charSet)  throws IOException{
        String content=get(url,charSet);
        if(!StringUtils.isBlank(content)){
            return JSONObject.parseObject(content);
        }
        return null;
    }

}
