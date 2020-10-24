package com.idragon.tool.excel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.idragon.tool.base.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


/**
 * @author xiaoshimei0305
 * @version 1.0
 * @date 2020/10/24 3:17 下午
 * @description excel  data read util
 */
public class ExcelReadUtils {
    /**
     *  Excel file mark before 2003 version
     */
    private static final String EXCEL_XLS = "xls";
    /**
     *  Excel file mark after 2007 version
     */
    private static final String EXCEL_XLSX = "xlsx";

    /**
     * Get data by file name
     * @param fileName file name
     * @param sheetName sheet name
     * @param targetClass target class
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> getDataByFileName(String fileName,String sheetName,Class<T> targetClass) throws IOException {
        JSONObject json=getDataByFileName(fileName);
        JSONArray ja=json.getJSONArray(sheetName);
        return ja.toJavaList(targetClass);
    }
    /**
     *  Get data by file name
     * @param fileName file name
     * @return
     * @throws IOException
     */
    public static JSONObject getDataByFileName(String fileName) throws IOException {
        return getDataByFileName(fileName,0);
    }
    /**
     *  Get data by file name
     * @param fileName file name
     * @param titleIndex title index
     * @return
     * @throws IOException
     */
    public static JSONObject getDataByFileName(String fileName,int titleIndex) throws IOException {
        Workbook wb = getWorkbookByFileName(fileName);
        return workbookToJSON(wb,titleIndex);
    }
    /**
     * format Workbook data to JSON
     * @param workbook Workbook
     * @param titleIndex index of title
     * @return
     */
    public static JSONObject workbookToJSON(Workbook workbook,int titleIndex){
        JSONObject data=new JSONObject();
        int sheetSize = workbook.getNumberOfSheets();
        for(int i=0;i<sheetSize;i++){
            Sheet sheet = workbook.getSheetAt(i);
            String sheetName=sheet.getSheetName();
            JSONArray list=new JSONArray();
            //col number
            int colNum=sheet.getRow(titleIndex).getPhysicalNumberOfCells();
            //row number
            int rowNum=sheet.getPhysicalNumberOfRows();
            //初始化字段名称,使用首行
            String[] titleNames=new String[colNum];
            Row titleRow = sheet.getRow(titleIndex);
            for(int j=0;j<colNum;j++){
                titleNames[j]=CellReadUtils.getValueByIndex(titleRow,j,"");
            }
            for(int j=1;j<rowNum;j++){
                if(j==titleIndex){
                    continue;
                }
                Row tempRow=sheet.getRow(j);
                JSONObject item=new JSONObject();
                boolean allEmpty=true;
                for(int k=0;k<=colNum;k++){
                    String value=CellReadUtils.getValueByIndex(tempRow,k,"");
                    if(StringUtils.isBlank(titleNames[k])){
                        continue;
                    }
                    item.put(titleNames[k],value);
                    if(!StringUtils.isBlank(value)){
                        allEmpty=false;
                    }
                }
                if(!allEmpty){
                    list.add(item);
                }
            }
            data.put(sheetName,list);
        }
        return data;
    }
    /**
     *  GET @Workbook object by fileName
     * @param fileName file name
     * @return Workbook
     * @throws IOException
     */
    public static Workbook getWorkbookByFileName(String fileName) throws IOException {
        if(StringUtils.isBlank(fileName)){
            throw new IllegalArgumentException("fileName is empty");
        }
        File file =new File(fileName.trim());
        if(!file.getName().endsWith(EXCEL_XLS)&&!file.getName().endsWith(EXCEL_XLSX)){
            throw new IllegalArgumentException("file format is error");
        }
        return getWorkbookByFileName(new FileInputStream(file),file.getName().endsWith(EXCEL_XLS));
    }

    /**
     *  Get @Workbook object by InputStream
     * @param inputStream InputStream
     * @param isXlsx check excel version
     * @return
     * @throws IOException
     */
    public static Workbook getWorkbookByFileName(InputStream inputStream, boolean  isXlsx) throws IOException {
        if(isXlsx){
            return new XSSFWorkbook(inputStream);
        }else{
            return new HSSFWorkbook(inputStream);
        }
    }


}
