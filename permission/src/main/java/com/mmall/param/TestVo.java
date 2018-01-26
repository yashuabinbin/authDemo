/*
 * TestVo.java 1.0.0 2018/1/25  下午 5:30 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/25  下午 5:30 created by lbb
 */
package com.mmall.param;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class TestVo {

    @NotBlank
    private String msg;

    @Max(value = 10, message = "max is 10")
    @NotNull
    private Integer id;

    @NotEmpty
    private List<String> str;

}
