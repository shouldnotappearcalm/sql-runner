package com.calm.sqlrunner.bean.dto;

import io.swagger.annotations.ApiModel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@ApiModel("测试传输对象")
public class TestDto {

    @Size(min = 1, max = 100, message = "长度为1-100")
    private String str1;

    @Size(min = 1, max = 4, message = "list长度为1-4")
    private List<String> list1;

}