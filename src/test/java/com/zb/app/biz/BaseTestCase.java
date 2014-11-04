/*
 * Copyright 2011-2016 import org.junit.Before; import org.junit.Test; import com.zb.app.biz.domain.TravelMemberDO;
 * import com.zb.app.biz.service.impl.TravelMemberService; l not disclose such Confidential Information and shall use it
 * only in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;

import com.zb.app.common.core.CommonServiceLocator;

/**
 * 测试抽象层
 * 
 * @author zxc Jun 18, 2014 5:12:42 PM
 */
@RunWith(JUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/spring/*.xml" })
public abstract class BaseTestCase extends CommonServiceLocator {

    protected static Logger logger = LoggerFactory.getLogger(BaseTestCase.class);

    @Before
    public void before() throws Exception {
        logger.error("##############Test before now init###############");
        onSetUp();
    }

    abstract public void onSetUp() throws Exception;

    @After
    public void cleanup() {
        logger.error("##############Test after now cleanup###############");
        destory();
    }

    /**
     * Assert string is blank: whitespace, empty ("") or null
     */
    protected void assertBlank(String str) {
        Assert.assertTrue(StringUtils.isBlank(str));
    }

    /**
     * Assert string is empty ("") or null
     */
    protected void assertEmpty(String str) {
        Assert.assertTrue(StringUtils.isEmpty(str));
    }

    /**
     * Assert string is not blank
     */
    protected void assertNotBlank(String str) {
        Assert.assertTrue(StringUtils.isNotBlank(str));
    }

    /**
     * Assert string is not empty
     */
    protected void assertNotEmpty(String str) {
        Assert.assertTrue(StringUtils.isNotEmpty(str));
    }

    /**
     * Assert string contains only unicode digits.
     */
    protected void assertNumeric(String str) {
        Assert.assertTrue(StringUtils.isNumeric(str));
    }

    /**
     * Assert string contains only unicode digits or space(' ').
     */
    protected void assertNumericSpace(String str) {
        Assert.assertTrue(StringUtils.isNumericSpace(str));
    }

    /**
     * Assert string contains only whitespace.
     */
    protected void assertWhitespace(String str) {
        Assert.assertTrue(StringUtils.isWhitespace(str));
    }

    /**
     * Assert array is null or empty(length==0)
     */
    protected <T> void assertNullOrEmpty(T[] array) {
        Assert.assertTrue(array == null || array.length == 0);
    }

    protected <T> void assertNotNullOrEmpty(T[] array) {
        Assert.assertTrue(array != null && array.length != 0);
    }

    /**
     * Assert list is null or empty
     */
    protected <T> void assertNullOrEmpty(List<T> list) {
        Assert.assertTrue(list == null || list.isEmpty());
    }

    protected <T> void assertNotNullOrEmpty(List<T> list) {
        Assert.assertTrue(list != null && !list.isEmpty());
    }
}
