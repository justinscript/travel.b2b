/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

/**
 * @author zxc Nov 10, 2014 4:08:43 PM
 */
public class WordTest {

    public static void main(String[] args) {
        KeepingWordMap map = new KeepingWordMap();
        map.addWord("中国");
        map.addWord("中国人");
        map.addWord("中国人民");

        map.printMap();

        map.removeWord("中国人");
        map.removeWord("中国");
        map.printMap();

        map.addWord("中国人");
        map.printMap();

        map.addWord("中国魂");
        map.printMap();

        map.removeWord("中国人民");
        map.printMap();

        map.addWord("中行");
        map.printMap();

        map.addWord("a");
        map.addWord("ab");
        map.addWord("abc");
        map.addWord("abcd");
        map.printMap();

        map.removeWord("ab");
        map.printMap();
    }
}
