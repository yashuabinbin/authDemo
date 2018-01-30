package com.mmall.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogWithBLOBs extends SysLog {

    private String oldValue;

    private String newValue;

}