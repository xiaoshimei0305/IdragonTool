package store.idragon.tool.excel;

import lombok.Data;

import java.util.Map;

/**
 * @author xiaoshimei0305
 * date  2021/5/24 10:01 上午
 * description excel表格数据读取对象
 * @version 1.0
 */
@Data
public class SheetConfig {
    /**
     * 标题列表序号
     */
    private int[] titleIndexList;
    /**
     * 数据开始序号
     */
    private  int startDataRowIndex;
    /**
     * 数据行序号
     */
    private int[] dataRowIndexList;
    /**
     * 编码列表，与excel编码对应
     */
    private String[] nameList;


    /**
     * 获取第一个标题列
     * @return
     */
    public int getFirstTitleIndex(){
        if(titleIndexList == null || titleIndexList.length < 1){
            return 0;
        }
        return titleIndexList[0];
    }
    /**
     * 获取最后一个标题列
     * @return
     */
    public int getLastTitleIndex(){
        if(titleIndexList == null || titleIndexList.length < 1){
            return 0;
        }
        return titleIndexList[titleIndexList.length-1];
    }

    /**
     * 获取开始数据读取序号
     * @return
     */
    public  int getStartDataRowIndex(){
        if(startDataRowIndex>0){
            return startDataRowIndex;
        }
        return getLastTitleIndex()+1;
    }

    /**
     * 获取指定列配置编码
     * @param colIndex
     * @return
     */
    public  String getName(int colIndex){
        if(this.nameList == null ||this.nameList.length < colIndex + 1){
            return null;
        }
        return this.nameList[colIndex];
    }

    /**
     * 获取默认表格读取配置
     * @return
     */
    public static SheetConfig getDefaultSheetConfig(){
        return new SheetConfig();
    }
}
