package com.calm.sqlrunner.bean.query;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

/**
 * 分页查询统一封装返回对象.
 *
 * @author gaozhirong on 2020/02/07
 * @version 1.0.0
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageResponseVo {

    /**
     * 分页信息.
     */
    private Pagination pageInfo;

    /**
     * 数据集合.
     */
    private Object data;

    /**
     * 构建 jpa 分页查询返回的数据.
     *
     * @param page page
     */
    public PageResponseVo(Page<?> page) {
        this(new Pagination(page), page.get().collect(Collectors.toList()));
    }

}
