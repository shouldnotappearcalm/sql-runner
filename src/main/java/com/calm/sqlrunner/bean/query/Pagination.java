package com.calm.sqlrunner.bean.query;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 返回给前端的分页数据对象.
 *
 * @author gaozhirong on 2020/02/07
 * @version 1.0.0
 */
@Getter
@Setter
public class Pagination {

    /**
     * 每页条数.
     */
    private Integer limit;

    /**
     * 偏移量.
     */
    private Integer offset;

    /**
     * 总页数.
     */
    private Integer pageCount;

    /**
     * 总记录数.
     */
    private long rowCount;

    /**
     * 构造方法，转换jpa 的{@link Page} 的分页.
     *
     * @param page 分页信息
     */
    Pagination(Page<?> page) {
        if (page == null) {
            throw new IllegalArgumentException("PageInfo is null");
        }
        Pageable pageable = page.getPageable();

        this.setLimit(pageable.getPageSize());
        int offsetTemp = page.getPageable().first().getPageNumber() - 1;
        this.setOffset(Math.max(offsetTemp, 0));
        this.setPageCount(page.getTotalPages());
        this.setRowCount(page.getTotalElements());
    }

    /**
     * 自定义分页对象.
     *
     * @param limit limit
     * @param offset offset
     * @param rowCount 总记录数
     */
    Pagination(Integer limit, Integer offset, long rowCount) {
        this.limit = limit;
        this.offset = offset;
        this.pageCount = Math.toIntExact((rowCount + limit - 1) / limit);
        this.rowCount = rowCount;
    }

}
