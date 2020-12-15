package store.idragon.tool.base.dto.result;

import lombok.Data;

import java.util.List;

/**
 * @author xiaoshimei0305
 * date  2020/12/12 10:36 下午
 * description 分页结果数据
 * @version 1.0
 */
@Data
public class PageResultParam<T> {
    /**
     * 数据总数
     */
    private long total;
    /**
     * 结果数据列表
     */
    private List<T> dataList;

    /**
     * 默认构造方法
     */
    public PageResultParam() {
    }

    /**
     * 结果数据组装
     * @param total 总数
     * @param dataList 结果列表
     */
    public PageResultParam(long total, List<T> dataList) {
        this.total = total;
        this.dataList = dataList;
    }
}
