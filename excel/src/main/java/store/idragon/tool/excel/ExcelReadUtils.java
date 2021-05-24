package store.idragon.tool.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import store.idragon.tool.base.StringUtils;

import java.io.IOException;
import java.util.List;


/**
 * @author xiaoshimei0305
 * @version 1.0
 * date 2020/10/24 3:17 下午
 * description excel  data read util
 */
public class ExcelReadUtils {

    /**
     * Get data by file name
     * @param fileName file name
     * @param sheetName sheet name
     * @param targetClass target class
     * @param <T> 数据类型
     * @return 读取到的结果
     * @throws IOException 文件数据读取存在IO异常情况
     */
    public static <T> List<T> getDataByFileName(String fileName,SheetConfig sheetConfig,String sheetName,Class<T> targetClass) throws IOException {
        JSONObject json=getDataByFileName(fileName,sheetConfig,sheetName);
        JSONArray ja=json.getJSONArray(sheetName);
        return ja.toJavaList(targetClass);
    }
    /**
     *  Get data by file name
     * @param fileName file name
     * @param titleIndex title index
     * @return Excel 文件数据
     * @throws IOException 文件数据读取存在IO异常情况
     */
    public static JSONObject getDataByFileName(String fileName,SheetConfig sheetConfig,String... sheetNames) throws IOException {
        Workbook wb = WorkBookUtils.getWorkbookByFileName(fileName);
        return workbookToJSON(wb,sheetConfig,sheetNames);
    }
    /**
     * format Workbook data to JSON
     * @param workbook Workbook
     * @param titleIndex index of title
     * @param ignoreBeforeTitleIndex 是否忽略标题之前数据内容，默认true
     * @param sheetNames 解析sheet列表，默认解析所有
     * @return 表格数据
     */
    private  static JSONObject workbookToJSON(Workbook workbook,SheetConfig sheetConfig,String... sheetNames){
        JSONObject data=new JSONObject();
        int sheetSize = workbook.getNumberOfSheets();
        // 检索表格sheet页
        for(int i=0;i<sheetSize;i++){
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName=sheet.getSheetName();
            // 判断sheet是否需要解析
            if(sheetNames!=null && sheetNames.length>0){
                boolean needParse = false;
                for(String item: sheetNames){
                    if(sheetName.equalsIgnoreCase(item)){
                        needParse = true;
                        break;
                    }
                }
                if(!needParse){
                    continue;
                }
            }
            // 或且最大列数
            int colNum=sheet.getRow(sheetConfig.getFirstTitleIndex()).getPhysicalNumberOfCells();
            // 初始化标题
            String[] titleNames = initTitleNames(colNum, sheet, sheetConfig);
            // 获取表格数据
            data.put(sheetName,getTableList(sheet,titleNames,sheetConfig));
        }
        return data;
    }


    /**
     * 获取表格标题
     * @param maxColumn 最大列表数
     * @param sheet 表格sheet对象
     * @param titleRowList 标题行索引
     * @return 实体key值
     */
    private static String[] initTitleNames(int maxColumn, Sheet sheet,SheetConfig sheetConfig){
        //初始化字段名称,使用首行
        String[] titleNames=new String[maxColumn];
        Row titleRow = sheet.getRow(sheetConfig.getLastTitleIndex());
        for(int j=0;j<maxColumn;j++){
            String configName=sheetConfig.getName(j);
            if(!StringUtils.isBlank(configName)){
                titleNames[j]=configName;
            }else{
                titleNames[j]=CellReadUtils.getValueByIndex(titleRow,j,j+"");
            }
        }
        return titleNames;
    }

    /**
     * 获取表格数据
     * @param sheet 表格对象
     * @param titleNames 表格标题
     * @param startRows 表格列表起始行（如果小于0，说明不进行无限行数据读取）
     * @param emunRowIndex 枚举需要读取数据对列表
     * @return
     */
    private static JSONArray getTableList(Sheet sheet,String[] titleNames,SheetConfig sheetConfig){
        JSONArray data=new JSONArray();
        //不满足读取数据条件，忽略数据读取。
        if(sheet==null || titleNames==null || titleNames.length<1){
            return data;
        }
        // 优先读取枚举行数据
        int[] dataRowIndexList = sheetConfig.getDataRowIndexList();
        if(dataRowIndexList != null &&dataRowIndexList.length>0){
            for(int rowIndex:dataRowIndexList){
                JSONObject item=getRowData(titleNames,sheet.getRow(rowIndex));
                if(item != null ){
                    data.add(item);
                }
            }
        }
        // 读取范围获取数据
        int maxRowNum=sheet.getPhysicalNumberOfRows();
        int startRows=sheetConfig.getStartDataRowIndex();
        if(startRows >= 0 && startRows<= maxRowNum){
            for(int rowIndex=startRows;rowIndex<maxRowNum;rowIndex++){
                JSONObject item=getRowData(titleNames,sheet.getRow(rowIndex));
                if(item != null ){
                    data.add(item);
                }
            }
        }
        return data;
    }

    /**
     * 获取行数据
     * @param titleNames
     * @param row
     * @return
     */
    private static JSONObject getRowData(String[] titleNames,Row row){
        if(titleNames == null || titleNames.length < 1 || row == null){
            return null;
        }
        JSONObject item=new JSONObject();
        boolean allEmpty=true;
        for(int i=0;i<titleNames.length;i++){
            String name=titleNames[i];
            String value=CellReadUtils.getValueByIndex(row,i,"");
            if(!StringUtils.isBlank(value)){
                allEmpty = false;
                item.put(name,value);
            }
        }
        if(allEmpty){
            return null;
        }else{
            return item;
        }
    }

}
