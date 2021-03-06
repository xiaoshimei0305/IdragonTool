package store.idragon.tool.word;



import java.io.*;
import store.idragon.tool.base.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiaoshimei0305
 * date  2021/5/24 4:34 下午
 * description word 相关的xml内容处理
 * @version 1.0
 */
public class XmlUtils {
    /**
     * 查找处理标签
     */
    public static final  String findSpliceFlag="\\{([\\s\\S]*?</w:t>[\\s\\S]*?<w:t>[\\s\\S]*?)\\}";
    /**
     *  合并拆分标签内容
     */
    public static final String filterExtFlagContent="</w:t>[\\s\\S]*?<w:t(>| [^<>]*?>)";

    /**
     *  查找For循环开始标签
     */
    public static final String startListFlag="<w:p ((?!w:p ).)*?\\$\\{&lt;#list(([a-z|A-Z| ])*?)&gt;\\}[\\s\\S]*?</w:p>";
    /**
     *  查找For循环结尾标签
     */
    public static final String endListFlag="<w:p ((?!w:p ).)*?\\$\\{&lt;/#list&gt;\\}[\\s\\S]*?</w:p>";

    /**
     * 模版文件生成freeMark模版内容
     * @param fileName 模版文件名称[word 另存为Word XML 文档(.xml)，切记不是：Word 2003 XML 文档(.xml)]
     * @return 模版内容
     * @throws IOException IOException
     */
    public static String getFreeMarkContentByFileName(String fileName) throws IOException {
        return formatFreeMarkModel(getFormatXmlContent(fileName));
    }
    /**
     * 格式化模版内容，防止一个表达式出现在多个标签中，造成无法识别的窘境
     * @param modelName 文件名称
     * @return 格式化xml内容
     * @throws IOException IOException
     */
    private static String getFormatXmlContent(String modelName) throws IOException {
        String content= StringUtils.getStringByInputStream(new FileInputStream(modelName));
        Pattern pattern=Pattern.compile(findSpliceFlag);
        Matcher matcher = pattern.matcher(content);
        String resultStr=content;
        while (matcher.find()){
            String oldFlag=matcher.group();
            //解决格式化空格之后，内容被忽视问题
            String newFlag=oldFlag.replaceAll(filterExtFlagContent,"");
            newFlag=newFlag.replaceAll("\\{","\\$\\{");
            resultStr = resultStr.replace(oldFlag,newFlag);
        }
        return resultStr;
    }
    /**
     * 转换为freeMark模型
     * @param modelContent 原始模型内容
     */
    private static String formatFreeMarkModel(String modelContent){
        // 1、替换结束标签
        modelContent=replaceEndForFlag(modelContent);
        // 1、替换开始标签，查找开始部分
        modelContent=replaceStartForFlag(modelContent);
       return modelContent;
    }

    /**
     * 替换for循环开始标签
     * @param modelContent
     * @return
     */
    private static String replaceStartForFlag(String modelContent){
        Pattern pattern=Pattern.compile(startListFlag);
        Matcher matcher = pattern.matcher(modelContent);
        String resultStr=modelContent;
        while (matcher.find()){
            String oldFlag=matcher.group();
           resultStr = resultStr.replace(oldFlag,"<#list"+matcher.group(2)+">");
        }
        return resultStr;
    }
    /**
     * 替换for循环结束标签
     * @param modelContent
     * @return
     */
    private  static  String replaceEndForFlag(String modelContent){
        Pattern pattern=Pattern.compile(endListFlag);
        Matcher matcher = pattern.matcher(modelContent);
        String resultStr=modelContent;
        while (matcher.find()){
            String oldFlag=matcher.group();
            resultStr = resultStr.replace(oldFlag,"</#list>");
        }
        return resultStr;
    }
}
