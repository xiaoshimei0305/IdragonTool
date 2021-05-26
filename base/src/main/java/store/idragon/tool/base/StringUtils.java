package store.idragon.tool.base;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

/**
 * @author xiaoshimei0305
 * @version 1.0
 * 2020/10/18 10:53 下午
 * {@inheritDoc}
 * <p>常用字符串处理工具,通过继承{@link org.apache.commons.lang3.StringUtils}引入apache常用工具【该工具在得到广大开发成员使用，简化重复劳动】。</p>
 * @see org.apache.commons.lang3.StringUtils
 */
public class StringUtils extends  org.apache.commons.lang3.StringUtils  {
    static {
        //BouncyCastle是一个开源的加解密解决方案，主页在http://www.bouncycastle.org/
        Security.addProvider(new BouncyCastleProvider());
    }

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

    /**
     *
     * AES解密数据内容[源自微信小程序加密内容验证时]
     * @param data  加密密文
     * @param key  密钥 【微信平台解密密钥问sessionKey】
     * @param iv   偏移量
     * @return 解密后密文
     * @throws Exception 解密异常情况
     */
    public static JSONObject aesDecryptToJson(String data, String key, String iv) throws Exception {
        return JSONObject.parseObject(getValue(aesDecrypt(data,key,iv),"{}"));
    }
    /**
     *
     * AES解密数据内容[源自微信小程序加密内容验证时]
     * @param data  加密密文
     * @param key  密钥 【微信平台解密密钥问sessionKey】
     * @param iv   偏移量
     * @return 解密后密文
     * @throws Exception 解密异常情况
     */
    public static String aesDecrypt(String data, String key, String iv) throws Exception {
        //被加密的数据
        byte[] dataByte = Base64.decodeBase64(data.getBytes("UTF-8"));
        //加密秘钥
        byte[] keyByte = Base64.decodeBase64(key.getBytes("UTF-8"));
        //偏移量
        byte[] ivByte = Base64.decodeBase64(iv.getBytes("UTF-8"));
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
        SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
        AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
        parameters.init(new IvParameterSpec(ivByte));
        // 初始化
        cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
        byte[] resultByte = cipher.doFinal(dataByte);
        if (null != resultByte && resultByte.length > 0) {
            String result = new String(resultByte, "UTF-8");
            return result;
        }
        return null;
    }


    /**
     * 从输入流中读入文本内容
     * @param inputStream 输入流
     * @return 文本内容
     * @throws IOException IOException
     */
    public static String getStringByInputStream(InputStream inputStream) throws IOException {
       return getStringByInputStream(inputStream,null);
    }

    /**
     * 从输入流中读入文本内容
     * @param inputStream 输入流
     * @param charsetName 字符集
     * @return 文本内容
     * @throws IOException IOException
     */
    public static String getStringByInputStream(InputStream inputStream,String charsetName) throws IOException {
        StringBuffer buffer=new StringBuffer();
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream,isBlank(charsetName)? "utf-8" : charsetName));
        line = reader.readLine();
        while (line != null) {
            buffer.append(line);
            buffer.append("\n");
            line = reader.readLine();
        }
        reader.close();
        inputStream.close();
        return buffer.toString();
    }
    /**
     * 输出内容到文本当
     * @param filePath 文件路径
     * @param content 输出内容
     * @throws IOException IOException
     */
    public static void outPutContentToFile(String filePath,String content) throws IOException {
        FileWriter writer = null;
        try {
            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }
            //目录不存在 则创建
            if (!file.getParentFile().exists()) {
                boolean mkdir = file.getParentFile().mkdirs();
                if (!mkdir) {
                    throw new RuntimeException("创建目标文件所在目录失败！");
                }
            }
            file.createNewFile();
            writer = new FileWriter(file);
            if (null != content) {
                writer.write(content);
            }
            writer.flush();
        } catch (IOException e) {
            throw e;
        } finally {
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException("文件写入后流关闭异常！");
                }
            }
        }

    }

}
