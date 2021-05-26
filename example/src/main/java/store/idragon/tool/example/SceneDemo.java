package store.idragon.tool.example;

import com.alibaba.fastjson.JSONObject;
import freemarker.template.TemplateException;
import store.idragon.tool.base.StringUtils;
import store.idragon.tool.excel.ExcelReadUtils;
import store.idragon.tool.word.FreemarkUtils;
import store.idragon.tool.word.XmlUtils;

import java.io.IOException;

/**
 * @author xiaoshimei0305
 * date  2021/5/26 9:40 上午
 * description 工具场景使用指导
 * @version 1.0
 */
public class SceneDemo {
    public static void main(String[] args) throws IOException, TemplateException {
        exportExcelToWord();
    }
    /**
     * 导出excel数据到word模版中
     */
    public static void exportExcelToWord() throws IOException, TemplateException {


        // 功能点模版（word点击另存为Word XML 文档(.xml)，切记不是：Word 2003 XML 文档(.xml)）
        String wordModelPath="/Users/chenxinjun/Downloads/poi/model/功能点模版.doc";
        // excel数据源
        String excelDataPath="/Users/chenxinjun/Downloads/poi/source/广东移动前端业务梳理.xlsx";
        //输出文件
        String resultFilePath="/Users/chenxinjun/Downloads/poi/model/功能需求说明书.doc";
        String modelContent= XmlUtils.getFreeMarkContentByFileName(wordModelPath);
        //默认第一行为表头，有特殊要求，请参考store.idragon.tool.excel.SheetConfig 进行配置
        JSONObject data = ExcelReadUtils.getDataByFileName(excelDataPath, "functionList");
        //模版名称，随意
        String result= FreemarkUtils.processTemplate("iisc",modelContent,data);
        //输出结果文件
        StringUtils.outPutContentToFile(resultFilePath,result);
    }

}
