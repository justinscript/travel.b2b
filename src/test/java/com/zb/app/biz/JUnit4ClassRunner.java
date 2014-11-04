/*
 * Copyright 2011-2016 import org.junit.Before; import org.junit.Test; import com.zb.app.biz.domain.TravelMemberDO;
 * import com.zb.app.biz.service.impl.TravelMemberService; l not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz;

import java.io.FileNotFoundException;

import org.junit.runners.model.InitializationError;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Log4jConfigurer;

/**
 * @author zxc Jun 18, 2014 4:19:23 PM
 */
public class JUnit4ClassRunner extends SpringJUnit4ClassRunner {

    static {
        try {
            Log4jConfigurer.initLogging("src/main/resources/config/log4j.properties");
        } catch (FileNotFoundException ex) {
            System.err.println("Cannot Initialize log4j");
        }
    }

    public JUnit4ClassRunner(Class<?> clazz) throws InitializationError {
        super(clazz);
    }
}
