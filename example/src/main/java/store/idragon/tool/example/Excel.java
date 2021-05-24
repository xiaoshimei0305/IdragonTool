package store.idragon.tool.example;

import com.alibaba.fastjson.JSONObject;
import store.idragon.tool.excel.ExcelReadUtils;
import store.idragon.tool.excel.SheetConfig;

import java.io.IOException;

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
        JSONObject data = ExcelReadUtils.getDataByFileName("/Users/chenxinjun/Downloads/广东移动前端业务梳理.xlsx", config,"功能列表");
        System.out.println(data.toJSONString());


    }

}
