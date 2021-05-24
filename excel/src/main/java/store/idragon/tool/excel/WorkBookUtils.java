package store.idragon.tool.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import store.idragon.tool.base.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author xiaoshimei0305
 * date  2021/5/24 10:50 上午
 * description
 * @version 1.0
 */
public class WorkBookUtils {
    /**
     *  Excel file mark before 2003 version
     */
    private static final String EXCEL_XLS = "xls";
    /**
     *  Excel file mark after 2007 version
     */
    private static final String EXCEL_XLSX = "xlsx";
    /**
     *  GET @Workbook object by fileName
     * @param fileName file name
     * @return Workbook
     * @throws IOException 文件数据读取存在IO异常情况
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
     * @return Workbook 对象
     * @throws IOException 文件数据读取存在IO异常情况
     */
    public static Workbook getWorkbookByFileName(InputStream inputStream, boolean  isXlsx) throws IOException {
        if(!isXlsx){
            return new XSSFWorkbook(inputStream);
        }else{
            return new HSSFWorkbook(inputStream);
        }
    }
}
