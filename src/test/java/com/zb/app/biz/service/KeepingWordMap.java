/*
 * Copyright 2011-2016 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.biz.service;

import java.util.HashMap;
import java.util.Set;

/**
 * @author zxc Nov 10, 2014 4:09:51 PM
 */
public class KeepingWordMap extends HashMap<Character, KeepingWordMap> {

    private static final long     serialVersionUID = 1L;

    private static KeepingWordMap dbKeepingWordMap = new KeepingWordMap();

    public static KeepingWordMap getDbKeepingWordMap() {
        return dbKeepingWordMap;
    }

    private boolean wordEnd = false;

    public boolean isWordEnd() {
        return wordEnd;
    }

    public void addWord(String word) {
        System.out.println("add keeping word:" + word);
        addWord(word.toCharArray(), 0);
    }

    public void removeWord(String word) {
        System.out.println("remove keeping word:" + word);
        removeWord(word.toCharArray(), 0);
    }

    public void addWord(char[] charArray, int index) {
        if (index >= charArray.length) {
            wordEnd = true;
            return;
        }
        char c = charArray[index];
        KeepingWordMap subMap = get(c);
        if (subMap != null) {
            subMap.addWord(charArray, ++index);
        } else {
            subMap = new KeepingWordMap();
            put(c, subMap);
            subMap.addWord(charArray, ++index);
        }
    }

    public void removeWord(char[] charArray, int index) {
        if (index >= charArray.length) {
            wordEnd = false;
            return;
        }
        char c = charArray[index];
        KeepingWordMap subMap = get(c);
        if (subMap != null) {
            if (index < charArray.length - 1) {
                subMap.removeWord(charArray, ++index);
                if (subMap.isEmpty() && !subMap.wordEnd) {
                    remove(c);
                }
            } else if (index == charArray.length - 1) {
                if (subMap.wordEnd) {
                    if (subMap.isEmpty()) {
                        remove(c);
                    }
                    subMap.removeWord(charArray, ++index);
                }
            }
        } else {
            remove(c);
        }
    }

    public void printMap() {
        System.out.println("-------------------");
        printMap("");
    }

    public void printMap(String pre) {
        Set<Character> keys = keySet();
        for (Character c : keys) {
            KeepingWordMap subMap = get(c);
            if (subMap != null) {
                if (subMap.wordEnd) {
                    System.out.println(pre + c);
                }
                subMap.printMap(pre + c);
            } else {
                System.out.println(pre + c);
            }
        }
    }

    // public char[] process(char[] str, KeepingWordMap map) {
    // if (str == null) {
    // return null;
    // }
    // char[] out = Arrays.copyOf(str, str.length);
    // int previousIndex = 0;
    // for (int i = 0; i < str.length; i++) {
    // do {
    // previousIndex = i;
    // i = findNextProcessIndex(str, i, map);
    // } while (previousIndex != i && i < str.length);
    //
    // if (i < str.length) {
    // out[i] = process(str[i]);
    // }
    // }
    // return out;
    // }

    private int findNextProcessIndex(char[] str, int index, KeepingWordMap map) {
        KeepingWordMap subMap = map.get(str[index]);
        int end = index;
        while (subMap != null && !subMap.isEmpty() && index < str.length) {
            if (subMap.isWordEnd()) {
                end = index + 1;
            }
            index++;
            if (index >= str.length) {
                break;
            }
            subMap = subMap.get(str[index]);

        }
        if (subMap != null && subMap.isWordEnd()) {
            end = index + 1;
        }
        return end;
    }
}
