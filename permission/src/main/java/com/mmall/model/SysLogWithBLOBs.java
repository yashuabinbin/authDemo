package com.mmall.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class SysLogWithBLOBs extends SysLog {
    private String oldValue;

    private String newValue;

}