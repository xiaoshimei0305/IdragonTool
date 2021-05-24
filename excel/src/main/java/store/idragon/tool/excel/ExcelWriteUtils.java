package store.idragon.tool.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import store.idragon.tool.base.StringUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiaoshimei0305
 * date  2020/11/20 10:37 上午
 * <p>常用Excel文件写入工具</p>
 * @version 1.0
 */
public class ExcelWriteUtils {
    /**
     * 表格内容同步接口
     * @param idCol 主键列
     * @param sourceFile 待同步文件
     * @param sourceSheetName 待同步文件sheet名称
     * @param targetFile 目标文件
     * @param targetSheetName 目标文件sheet名称
     * @param ignoreIds  忽略主键【原始表格中找到指定主键[idCol列中的值]，忽略不同步】
     * <p> 将原始表格数据更新至目标表格：
     *     1、原始表格：sourceFile[sourceSheetName]
     *     2、目标表格：targetFile[targetSheetName]
     *     3、规则：  主键列【idCol】作为数据唯一标示 ，在目标表中找到对应行数据进行更新【无数据则插入表格最后】               \
     * </p>
     * @throws IOException 文件读写过程中可能存在IO异常情况
     */
    public static void syncContent(int idCol,String sourceFile,String sourceSheetName,String targetFile,String targetSheetName,String... ignoreIds) throws IOException {
        Workbook sourceWorkBook = WorkBookUtils.getWorkbookByFileName(sourceFile);
        Workbook targetWorkBook = WorkBookUtils.getWorkbookByFileName(targetFile);
        if(sourceWorkBook!=null&&targetWorkBook!=null){
            Sheet sourceSheet = sourceWorkBook.getSheet(sourceSheetName);
            Sheet targetSheet = targetWorkBook.getSheet(targetSheetName);
            if(sourceSheet!=null &&targetSheet!=null){
                Map<String,Integer> targetIndex=new HashMap<>(500);
                //初始化原始数据，当前最大行数
                int rowNum=targetSheet.getPhysicalNumberOfRows();
                for(int i=0;i<rowNum;i++){
                    Row row = targetSheet.getRow(i);
                    if(row!=null){
                        String id=CellReadUtils.getValueByIndex(row,idCol,"");
                        if(!StringUtils.isBlank(id)){
                            targetIndex.put(id,i);
                        }
                    }
                }
                // 循环更新数据
                int sourceNum=sourceSheet.getPhysicalNumberOfRows();
                for(int i=0;i<sourceNum;i++){
                    Row sourceRow = sourceSheet.getRow(i);
                    String id=CellReadUtils.getValueByIndex(sourceRow,idCol,"");
                    if(!StringUtils.isBlank(id)){
                        if(ignoreIds!=null&&ignoreIds.length>0){
                            for(String ignoreId:ignoreIds){
                                if(id.equalsIgnoreCase(ignoreId)){
                                    continue;
                                }
                            }
                        }
                        int  currentIndex=-1;
                        if(targetIndex.containsKey(id)){
                            currentIndex=targetIndex.get(id);
                        }else{
                            currentIndex=rowNum++;
                            targetIndex.put(id,currentIndex);
                            targetSheet.createRow(currentIndex);
                        }
                        Row targetRow=targetSheet.getRow(currentIndex);
                        System.out.println("同步数据行： "+i);
                        int sourceCellSize = sourceRow.getPhysicalNumberOfCells();
                        int targetCellSize =targetRow.getPhysicalNumberOfCells();
                        int maxCellsSize =targetCellSize>sourceCellSize?targetCellSize:sourceCellSize;
                        // 复制内容到目标表格中
                        for(int j=0;j<maxCellsSize;j++){
                            Cell targetCell=targetRow.getCell(j);
                            if(targetCell==null){
                                targetCell=targetRow.createCell(j);
                            }
                            targetCell.setCellValue(CellReadUtils.getValueByIndex(sourceRow,j,""));
                        }
                    }
                }
                FileOutputStream out = new FileOutputStream(targetFile);
                targetWorkBook.write(out);
                out.close();
            }
        }
    }
    /**
     * 表格内容同步接口
     * @param idCol 主键列
     * @param ignoreId 忽略主键【原始表格中找到指定主键[idCol列中的值]，忽略不同步】
     * @param sheetName 表格名称
     * @param sourceFile 待同步文件
     * @param targetFile 目标文件
     * <p> 将原始表格数据更新至目标表格：
     *     1、原始表格：sourceFile[sheetName]
     *     2、目标表格：targetFile[sheetName]
     *     3、规则：  主键列【idCol】作为数据唯一标示 ，在目标表中找到对应行数据进行更新【无数据则插入表格最后】               \
     * </p>
     * @throws IOException 文件读写过程中可能存在IO异常情况
     */
    public static void syncContent(int idCol,String ignoreId,String sheetName,String sourceFile,String targetFile) throws IOException {
        syncContent(idCol,sourceFile,sheetName,targetFile,sheetName,ignoreId);
    }
}
