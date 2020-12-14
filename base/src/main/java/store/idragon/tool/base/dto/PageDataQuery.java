package store.idragon.tool.base.dto;

import lombok.Data;

/**
 * @author xiaoshimei0305
 * date  2020/12/12 10:35 下午
 * description
 * @version 1.0
 */
@Data
public class PageDataQuery<Q> extends DataQuery<Q> {
    /**
     * 分页信息
     */
    private PageQueryParam pageInfo;
    /**
     *  查询信息
     */
    private Q queryInfo;
}
