package store.idragon.tool.example;

import freemarker.template.TemplateException;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.example.bean.Person;
import store.idragon.tool.word.FreemarkUtils;
import store.idragon.tool.word.XmlUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiaoshimei0305
 * date  2021/5/25 9:28 下午
 * description
 * @version 1.0
 */
public class WordTest {

    public static void main(String[] args) throws IOException, TemplateException {
        String wordModel="/Users/chenxinjun/Downloads/poi/model/modelPerson.xml";
        String outFile="/Users/chenxinjun/Downloads/poi/model/result.doc";
//        String baseXmlModel= XmlUtils.getFormatXmlContent(wordModel);
//        String freeMarkModel=XmlUtils.formatFreeMarkModel(baseXmlModel);
        String freeMarkModel=XmlUtils.getFreeMarkContentByFileName(wordModel);
        Map<String, Object> data=new HashMap<>(10);
        List<Person> list=new ArrayList<Person>();
        list.add(new Person("1822918123123","xinjun","2021year","hhhhhhhh"));
        list.add(new Person("18229181231232323","xinju23n","203221year","hhsshhhhhh"));
        data.put("userList",list);
        data.put("cherry","fuck you Name");
        String result= FreemarkUtils.processTemplate("ch",freeMarkModel,data);
        StringUtils.outPutContentToFile(outFile,result);
    }
}
