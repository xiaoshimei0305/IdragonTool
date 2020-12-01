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
 * description excel文档修改工具
 * @version 1.0
 */
public class ExcelWriteUtils {
    /**
     *  同步内容到指定表格【不删除数据】
     * @param idCol 主键列
     * @param sourceFile 待同步文件
     * @param sourceSheetName 待同步文件sheet名称
     * @param targetFile  目标文件
     * @param targetSheetName 目标文件sheet名称
     * @param ignoreIds 忽略主键
     */
    public static void syncContent(int idCol,String sourceFile,String sourceSheetName,String targetFile,String targetSheetName,String... ignoreIds) throws IOException {
        Workbook sourceWorkBook = ExcelReadUtils.getWorkbookByFileName(sourceFile);
        Workbook targetWorkBook = ExcelReadUtils.getWorkbookByFileName(targetFile);
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
     * 同步内容到指定表格
     * @param idCol 主键列
     * @param ignoreId 忽略主键
     * @param sheetName 表格名称
     * @param sourceFile 待同步文件
     * @param targetFile 目标文件
     */
    public static void syncContent(int idCol,String ignoreId,String sheetName,String sourceFile,String targetFile) throws IOException {
        syncContent(idCol,sourceFile,sheetName,targetFile,sheetName,ignoreId);
    }

    public static void main(String[] args) throws IOException {
        String sourceFile="/Users/chenxinjun/Downloads/1634879745218265039_陈新俊-Bug-1120.xlsx";
        String targetFiel="/Users/chenxinjun/work/document/dfshop/项目管理/东购BUG情况汇总.xlsx";
        syncContent(0,"ID","Bug",sourceFile,targetFiel);
        System.out.println("同步完成");
    }


}
