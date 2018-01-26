/*
 * Test.java 1.0.0 2018/1/26  下午 1:16 
 * Copyright © 2014-2017,52mamahome.com.All rights reserved
 * history :
 *     1. 2018/1/26  下午 1:16 created by lbb
 */

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test {

    @org.junit.Test
    public void testLog(){
        log.info("info");
        log.debug("debug");
        log.warn("warn");
        log.error("error");
        log.trace("trace");
    }

}
