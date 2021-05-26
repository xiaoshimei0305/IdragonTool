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
     * @return 第一个标题行
     */
    public int getFirstTitleIndex(){
        if(titleIndexList == null || titleIndexList.length < 1){
            return 0;
        }
        return titleIndexList[0];
    }
    /**
     * 获取最后一个标题列
     * @return 最后一个标题行序号
     */
    public int getLastTitleIndex(){
        if(titleIndexList == null || titleIndexList.length < 1){
            return 0;
        }
        return titleIndexList[titleIndexList.length-1];
    }

    /**
     * 获取开始数据读取序号
     * @return 开始解析excel数据序号
     */
    public  int getStartDataRowIndex(){
        if(startDataRowIndex>0){
            return startDataRowIndex;
        }
        return getLastTitleIndex()+1;
    }

    /**
     * 获取指定列配置编码
     * @param colIndex 索引序列号
     * @return 对应索引名称
     */
    public  String getName(int colIndex){
        if(this.nameList == null ||this.nameList.length < colIndex + 1){
            return null;
        }
        return this.nameList[colIndex];
    }

    /**
     * 获取默认表格读取配置
     * @return 默认表格导出配置信息
     */
    public static SheetConfig getDefaultSheetConfig(){
        return new SheetConfig();
    }
}
