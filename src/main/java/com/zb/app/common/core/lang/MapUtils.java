/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.core.lang;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author zxc Sep 1, 2014 11:56:02 PM
 */
public class MapUtils {

    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        return sort(map, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });
    }

    public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(Map<K, V> map) {
        return sort(map, new Comparator<Map.Entry<K, V>>() {

            @Override
            public int compare(Entry<K, V> o1, Entry<K, V> o2) {
                return o1.getKey().compareTo(o2.getKey());
            }
        });
    }

    public static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<Map.Entry<K, V>> comparator) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, comparator);
        Map<K, V> result = new LinkedHashMap<K, V>(list.size());
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    public static void main(String[] args) {
        Map<String, List<Integer>> complexMap = new HashMap<String, List<Integer>>();
        complexMap.put("4", Wrapper.collection(4));
        complexMap.put("3", Wrapper.collection(1, 2, 4, 5, 6));
        complexMap.put("1", Wrapper.collection(1, 2, 3));
        complexMap.put("2", Wrapper.collection(4, 5));

        Map<String, List<Integer>> sort = sort(complexMap, new Comparator<Map.Entry<String, List<Integer>>>() {

            @Override
            public int compare(Entry<String, List<Integer>> o1, Entry<String, List<Integer>> o2) {
                return o1.getValue().size() - o2.getValue().size();
            }

        });

        System.out.println(sort);

        System.out.println("SortByKey");
        System.out.println(sortByKey(complexMap));

        List<Integer> collection = Wrapper.collection(1, 2, 3, 4, 5, 6);
        System.out.println(collection.subList(0, collection.size() - 1));
        System.out.println(collection);

    }
}
