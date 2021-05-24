package store.idragon.tool.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import store.idragon.tool.base.StringUtils;

/**
 * @author xiaoshimei0305
 * @version 1.0
 * date 2020/10/24 3:17 下午
 * description excel cell data read util
 */
public class CellReadUtils {
    /**
     * 获取指定cell值
     * @param row 数据行
     * @param index 列标示
     * @param defaultValue 默认值
     * @return 读取结果数据
     */
    public static String getValueByIndex(Row row, int index, String defaultValue){
        if(row!=null){
            Cell cell = row.getCell(index);
            if(cell!=null){
                String resultStr;
                CellType cellType = cell.getCellType();
                switch (cellType){
                    case STRING:
                        resultStr=cell.getStringCellValue();
                        break;
                    case FORMULA:
                        resultStr=cell.getCellFormula();
                        break;
                    case NUMERIC:
                        resultStr=Double.toString(cell.getNumericCellValue());
                        break;
                    default:
                        resultStr="";
                }
                return StringUtils.getValue(resultStr,defaultValue);
            }
        }
        return defaultValue;
    }
}
