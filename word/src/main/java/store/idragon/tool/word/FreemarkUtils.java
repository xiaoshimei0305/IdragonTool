package store.idragon.tool.word;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author xiaoshimei0305
 * date  2021/5/25 9:12 下午
 * description 模版替换工具
 * @version 1.0
 */
public class FreemarkUtils {

    /**
     * 解析模板
     * @param templateName 模版名称
     * @param templateValue 模版内容
     * @param data 渲染数据
     * @return 渲染后结果
     * @throws IOException IOException
     * @throws TemplateException TemplateException
     */
    public static String processTemplate(String templateName, String templateValue,Map<String,Object> data) throws IOException, TemplateException {
        StringWriter stringWriter = new StringWriter();
        Template template = new Template(templateName, templateValue, configuration());
        template.process(data, stringWriter);
        return stringWriter.toString();
    }

    /**
     * 配置 freemarker configuration
     *
     * @return
     */
    private static Configuration configuration() {
        Configuration configuration = new Configuration();
        StringTemplateLoader templateLoader = new StringTemplateLoader();
        configuration.setTemplateLoader(templateLoader);
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassicCompatible(true);
        return configuration;
    }
}
