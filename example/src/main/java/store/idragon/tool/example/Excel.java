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
    /**
     * 格式化读取表格数据，并生成实体列表使用案例
     * @param args 启动参数
     * @throws IOException IOException
     */
    public static void main(String[] args) throws IOException {
        String fileName="/Users/chenxinjun/Downloads/poi/source/广东移动前端业务梳理.xlsx";
        String sheetName="功能列表";
        String wordModel="/Users/chenxinjun/Downloads/poi/model/modelPerson.doc";
        List<FuncEntity> data=ExcelReadUtils.getDataByFileName(fileName,sheetName,FuncEntity.class);
        System.out.println(data);


    }

}
