package store.idragon.tool.example;

import com.alibaba.fastjson.JSONObject;
import store.idragon.tool.example.bean.FuncEntity;
import store.idragon.tool.excel.ExcelReadUtils;
import store.idragon.tool.excel.SheetConfig;

import java.io.IOException;
import java.util.List;

/**
 * @author xiaoshimei0305
 * date  2021/5/21 11:03 上午
 * description
 * @version 1.0
 */
public class Excel {
    public static void main(String[] args) throws IOException {
        SheetConfig config=SheetConfig.getDefaultSheetConfig();
        config.setTitleIndexList(new int[]{1});
        config.setNameList(new String[]{"codeSeq","funcName","","appName"});
        String fileName="/Users/chenxinjun/Downloads/poi/source/广东移动前端业务梳理.xlsx";
        String sheetName="功能列表";
        String wordModel="/Users/chenxinjun/Downloads/poi/model/modelPerson.doc";

        List<FuncEntity> data=ExcelReadUtils.getDataByFileName(fileName,config,sheetName,FuncEntity.class);
       // JSONObject data = ExcelReadUtils.getDataByFileName(fileName, config,sheetName);
        System.out.println(data);


    }

}
