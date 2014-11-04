/*
 * Copyright 2014-2017 ZuoBian.com All right reserved. This software is the confidential and proprietary information of
 * ZuoBian.com ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only
 * in accordance with the terms of the license agreement you entered into with ZuoBian.com.
 */
package com.zb.app.common.file;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zb.app.common.util.ConfigMap;

/**
 * 对.properties属性文件相关操作的工具类，该类无法被继承。
 * 
 * @author zxc Jun 16, 2014 12:11:08 AM
 */
public class PropertiesHelper {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesHelper.class);

    /**
     * PropertiesHelper类的私有构造方法
     */
    private PropertiesHelper() {

    }

    /**
     * 根据给定的资源文件的路径值，加载单个资源文件，返回ConfigMap类对象的引用。
     * 
     * @param filePath 资源文件的路径值
     * @return 给定资源文件所对应的ConfigMap类对象的引用。
     */
    public static ConfigMap loadProperties(String filePath) {
        ConfigMap configMap = new ConfigMap();
        try {
            InputStream path = PropertiesHelper.class.getResourceAsStream(filePath);
            Properties pros = new Properties();
            pros.load(path);
            @SuppressWarnings("rawtypes")
            Iterator ir = pros.keySet().iterator();
            while (ir.hasNext()) {
                Object key = ir.next();
                Object value = pros.getProperty((String) key);
                setKeyAndValue((String) key, value.toString(), configMap);
            }
            path.close();
            configMap.printConfigMap();
        } catch (Exception e) {
            logger.info("------读取properties配置文件" + filePath + "时，出错:" + e.getMessage().toString());
        }
        return configMap;
    }

    /**
     * 根据给定的资源文件的路径值，加载单个资源文件，返回ConfigMap类对象的引用。
     * 
     * @param filePath 资源文件的路径值
     * @return 给定资源文件所对应的ConfigMap类对象的引用。
     */
    public static ConfigMap loadProperties2Map(String filePath) {
        ConfigMap configMap = new ConfigMap();
        try {
            InputStream path = new FileInputStream(filePath);
            Properties pros = new Properties();
            pros.load(path);
            @SuppressWarnings("rawtypes")
            Iterator ir = pros.keySet().iterator();
            while (ir.hasNext()) {
                Object key = ir.next();
                Object value = pros.getProperty((String) key);
                setKeyAndValue((String) key, value.toString(), configMap);
            }
            path.close();
            configMap.printConfigMap();
        } catch (Exception e) {
            logger.info("------读取properties配置文件" + filePath + "时，出错:" + e.getMessage().toString());
        }
        return configMap;
    }

    /**
     * <pre>
     * 约定如下：
     *      1.I_开头表示Integer类型
     *      2.B_开头表示Boolean类型
     *      3.F_开头表示Float类型
     *      4.S_开头表示String类型
     *      5.IA_开头表示Integer数组类型
     *      6.BA_开头表示Boolean数组类型
     *      7.FA_开头表示Float数组类型
     *      8.SA_开头表示String数组类型
     * </pre>
     * 
     * @param key
     * @param value
     * @param configMap
     */
    private static void setKeyAndValue(String key, String value, ConfigMap configMap) {
        logger.debug("Key=" + key + " Value=" + value);
        if (key.startsWith("I_")) {
            configMap.putKV(key, Integer.parseInt(value.trim()));
        } else if (key.startsWith("B_")) {
            configMap.putKV(key, Boolean.parseBoolean(value.trim()));
        } else if (key.startsWith("F_")) {
            configMap.putKV(key, Float.parseFloat(value.trim()));
        } else if (key.startsWith("S_")) {
            configMap.putKV(key, value.trim());
        } else if (key.startsWith("IA_")) {
            String[] sp = value.trim().split(";");
            int v[] = new int[sp.length];
            for (int i = 0; i < sp.length; i++) {
                v[i] = Integer.parseInt(sp[i].trim());
            }
            configMap.putKV(key, v);
        } else if (key.startsWith("BA_")) {
            String[] sp = value.trim().split(";");
            boolean v[] = new boolean[sp.length];
            for (int i = 0; i < sp.length; i++) {
                v[i] = Boolean.parseBoolean(sp[i].trim());
            }
            configMap.putKV(key, v);
        } else if (key.startsWith("FA_")) {
            String[] sp = value.trim().split(";");
            float v[] = new float[sp.length];
            for (int i = 0; i < sp.length; i++) {
                v[i] = Float.parseFloat(sp[i].trim());
            }
            configMap.putKV(key, v);
        } else if (key.startsWith("SA_")) {
            configMap.putKV(key, value.trim().split(";"));
        } else {
            logger.error("The kv is error ! please check it . ----- Key=" + key + " Value=" + value);
        }
    }

    /**
     * 根据给定的资源文件的路径值，加载单个资源文件，返回Properties类对象的引用。若有异常产生，则Properties类对象的引用为null。
     * 
     * @param filePath 资源文件的路径值
     * @return 给定资源文件所对应的Properties类对象的引用。
     */
    public static Properties loadPropertiesFile(String filePath) {
        Properties properties = new Properties();
        InputStream is = null;

        try {
            try {
                is = new FileInputStream(filePath);
                properties.load(is);
            } finally {
                if (is != null) {
                    is.close();
                    is = null;
                }
            }
        } catch (Exception e) {
            properties = null;
            logger.info("------读取properties配置文件" + filePath + "时，出错:" + e.getMessage().toString());
        }

        return properties;
    }

    /**
     * 根据多个资源文件的路径值，加载多个资源文件。
     * 
     * @param filePaths 多个资源文件的路径值
     * @return 存放Properties类型引用的散列表，该散列表以资源文件的路径值为键。
     * @see PropertiesHelper#loadPropertiesFile(String)
     */
    public static Map<String, Properties> loadPropertiesFiles(List<String> filePaths) {
        Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

        for (String filePath : filePaths) {
            propertiesMap.put(filePath, loadPropertiesFile(filePath));
        }

        return propertiesMap;
    }

    /**
     * 根据给定的文件路径值，存放properties引用相关的资源文件到磁盘上。
     * 
     * @param filePath 目标资源文件的路径值
     * @param properties Properties类型的引用
     * @return 操作成功，返回true；否则，返回false。
     * @see PropertiesHelper#storePropertiesFile(String, Map)
     */
    public static boolean storePropertiesFile(String filePath, Properties properties) {
        return storePropertiesFile(filePath, getProperty(properties));
    }

    /**
     * 根据给定的文件路径值和欲存放的键值对，将目标资源文件存放到磁盘上。
     * 
     * @param filePath 目标资源文件的路径值
     * @param propertyMap 所有键值对
     * @return 操作成功，返回true；否则，返回false。
     */
    public static boolean storePropertiesFile(String filePath, Map<String, String> propertyMap) {
        Properties properties = new Properties();
        FileWriter writer = null;

        try {
            try {
                writer = new FileWriter(filePath);

                for (Map.Entry<String, String> entry : propertyMap.entrySet()) {
                    properties.put(entry.getKey(), entry.getValue());
                }

                properties.store(writer, null);
            } finally {
                if (writer != null) {
                    writer.close();
                    writer = null;
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 处理多个资源文件存放到磁盘上。
     * 
     * @param filePaths 多个资源文件的路径值集合
     * @param propertyMaps 多个资源文件中的键值对集合
     * @return 操作成功，返回true；否则，返回false。
     * @see PropertiesHelper#storePropertiesFile(String, Map)
     */
    public static boolean storePropertiesFiles(List<String> filePaths, List<Map<String, String>> propertyMaps) {
        int filePathSize = filePaths.size();

        if (filePathSize != propertyMaps.size()) {
            return false;
        }

        for (int i = 0; i < filePathSize; i++) {
            if (!storePropertiesFile(filePaths.get(i), propertyMaps.get(i))) {
                return false;
            }
        }

        return true;
    }

    /**
     * 获得给定键所关联的值的字符串形式。若键不存在，则返回给定的默认值。若properties为null，则返回null。
     * 
     * @param key 键
     * @param defaultValue 默认值
     * @param properties 目标资源文件所对应的Properties类对象的引用
     * @return 给定键所关联的值的字符串形式
     */
    public static String getString(String key, String defaultValue, Properties properties) {
        return (properties == null) ? null : properties.getProperty(key, defaultValue);
    }

    /**
     * 获得给定键所关联的值的字符串形式。若键不存在，则返回给定的默认值。若properties为null，则返回null。
     * 
     * @param key 键
     * @param defaultValue 默认值
     * @param properties 目标资源文件所对应的Properties类对象的引用
     * @return 给定键所关联的值的字符串形式
     */
    public static Integer getInteger(String key, Integer defaultValue, Properties properties) {
        String stringValue = (properties == null) ? null : properties.getProperty(key, defaultValue.toString());
        Integer value = null;

        if (stringValue == null) {
            return null;
        }

        try {
            value = Integer.valueOf(stringValue);
        } catch (NumberFormatException e) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 获得给定键所关联的值的布尔形式。若键不存在，则返回给定的默认值。若键所对应的值不为true，则返回的是false。若properties为null，则返回null。
     * 
     * @param key 键
     * @param defaultValue 默认值
     * @param properties 目标资源文件所对应的Properties类对象的引用
     * @return 给定键所关联的值的布尔形式
     */
    public static Boolean getBoolean(String key, Boolean defaultValue, Properties properties) {
        String stringValue = (properties == null) ? null : properties.getProperty(key, defaultValue.toString());
        return (stringValue == null) ? null : Boolean.valueOf(stringValue);
    }

    /**
     * 获得给定键所关联的值的浮点型形式。若键不存在，则返回给定的默认值。若properties为null，则返回null。
     * 
     * @param key 键
     * @param defaultValue 默认值
     * @param properties 目标资源文件所对应的Properties类对象的引用
     * @return 给定键所关联的值的浮点型形式
     */
    public static Double getDouble(String key, Double defaultValue, Properties properties) {
        String stringValue = (properties == null) ? null : properties.getProperty(key, defaultValue.toString());
        Double value = null;

        if (stringValue == null) {
            return null;
        }

        try {
            value = Double.valueOf(stringValue);
        } catch (NumberFormatException e) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 获得给定键所关联的值的日期形式。若键不存在，则返回给定的默认值。若properties为null，则返回null。
     * 
     * @param key 键
     * @param defaultValue 默认值
     * @param properties 目标资源文件所对应的Properties类对象的引用
     * @return 给定键所关联的值的日期形式
     */
    public static Date getDate(String key, Date defaultValue, Properties properties) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String stringValue = (properties == null) ? null : properties.getProperty(key, sdf.format(defaultValue));
        Date value = null;

        if (stringValue == null) {
            return null;
        }

        try {
            value = sdf.parse(stringValue);
        } catch (ParseException e) {
            value = defaultValue;
        }

        return value;
    }

    /**
     * 根据给定的若干个键和Properties类型的引用，获得资源文件中若干个指定的键值对。
     * 
     * @param keys 若干个键
     * @param properties Properties类型引用
     * @return 若干个指定的键值对
     */
    public static Map<String, String> getProperty(List<String> keys, Properties properties) {
        Map<String, String> propertyMap = new HashMap<String, String>();

        for (String key : keys) {
            propertyMap.put(key, properties.getProperty(key));
        }

        return propertyMap;
    }

    /**
     * 根据给定的若干个键和资源文件的路径值，获得资源文件中若干个指定的键值对。该方法会调用getProperty(List, Properties)方法。
     * 
     * @param keys 若干个键
     * @param filePath 资源文件的路径值
     * @return 若干个指定的键值对
     * @see PropertiesHelper#getProperty(List, Properties)
     */
    public static Map<String, String> getProperty(List<String> keys, String filePath) {
        return getProperty(keys, loadPropertiesFile(filePath));
    }

    /**
     * 获得给定Properties类型引用所对应的资源文件中的所有键值对。
     * 
     * @param properties Properties类型的引用
     * @return 资源文件中的所有键值对
     */
    public static Map<String, String> getProperty(Properties properties) {
        Map<String, String> propertyMap = new HashMap<String, String>();

        for (Iterator<Object> iter = properties.keySet().iterator(); iter.hasNext();) {
            String key = iter.next().toString();
            propertyMap.put(key, properties.getProperty(key));
        }

        return propertyMap;
    }

    /**
     * 根据给定的资源文件的路径，获得资源文件中的所有键值对。该方法会调用getProperty(Properties)方法。
     * 
     * @param filePath 资源文件的路径值
     * @return 资源文件中的所有键值对
     * @see PropertiesHelper#getProperty(Properties)
     */
    public static Map<String, String> getProperty(String filePath) {
        return getProperty(loadPropertiesFile(filePath));
    }
}
