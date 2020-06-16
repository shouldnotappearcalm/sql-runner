package com.calm.sqlrunner.bean.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.calm.sqlrunner.bean.validate.Update;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 基础域对象.
 *
 * @author gaozhirong on 2020/02/05
 * @version 1.0.0
 */
@Getter
@Setter
@ToString
@MappedSuperclass
@Accessors(chain = true)
@ApiModel(description = "基础域对象")
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {

    /**
     * ID.
     */
    @Id
    @Column(name = "id")
    @ApiModelProperty("Log自增主键")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @NotNull(message = "主键不为空", groups = {Update.class})
    private Long id;


    @CreatedDate
    @ApiModelProperty("创建时间")
    @Column(name = "create_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    /**
     * 最后更新时间.
     */
    @LastModifiedDate
    @ApiModelProperty("更新时间")
    @Column(name = "update_time")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

}

