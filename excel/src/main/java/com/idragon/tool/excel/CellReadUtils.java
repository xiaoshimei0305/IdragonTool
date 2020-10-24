package com.idragon.tool.excel;

import com.idragon.tool.base.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;

/**
 * @author xiaoshimei0305
 * @version 1.0
 * @date 2020/10/24 3:17 下午
 * @description excel cell data read util
 */
public class CellReadUtils {
    /**
     * 获取指定cell值
     * @param row
     * @param index
     * @return
     */
    public static String getValueByIndex(Row row, int index, String defaultValue){
        if(row!=null){
            Cell cell = row.getCell(index);
            if(cell!=null){
                cell.setCellType(CellType.STRING);
                return StringUtils.isBlank(cell.getStringCellValue())?defaultValue:cell.getStringCellValue();
            }
        }
        return defaultValue;
    }
}
