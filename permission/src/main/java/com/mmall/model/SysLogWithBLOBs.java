package com.mmall.model;

import lombok.*;

@Getter
@Setter
public class SysLogWithBLOBs extends SysLog {

    private String oldValue;

    private String newValue;

}