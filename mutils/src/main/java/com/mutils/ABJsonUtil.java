package com.mutils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * 类描述：ABJsonUtil json工具类
 * 创建人：Michael
 * 创建时间：2015/12/15 9:40
 * 修改人：Michael
 * 修改时间：2015/12/15 9:40
 * 修改备注：
 */
public class ABJsonUtil {
    /**
     * 从JSONObject中获取对应的字符串
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the String
     */
    public static String getString(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getString(key) : "";
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 从JSONObject中获取对应的字符串
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the String
     */
    public static String getString(JSONObject jo, String key, String defaultValue) {
        try {
            return jo.has(key) ? jo.getString(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的int数字
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the int
     */
    public static int getInt(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getInt(key) : 0;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 从JSONObject中获取对应的int数字
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the int
     */
    public static int getInt(JSONObject jo, String key, int defaultValue) {
        try {
            return jo.has(key) ? jo.getInt(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的long数字
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the long
     */
    public static long getLong(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getLong(key) : 0l;
        } catch (Exception e) {
            return 0l;
        }
    }

    /**
     * 从JSONObject中获取对应的long数字
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the long
     */
    public static long getLong(JSONObject jo, String key, long defaultValue) {
        try {
            return jo.has(key) ? jo.getLong(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的boolean
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the boolean
     */
    public static boolean getBoolean(JSONObject jo, String key) {
        try {
            return jo.has(key) && jo.getBoolean(key);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从JSONObject中获取对应的boolean
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the boolean
     */
    public static boolean getBoolean(JSONObject jo, String key, boolean defaultValue) {
        try {
            return jo.has(key) ? jo.getBoolean(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的double数字
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the double
     */
    public static double getDouble(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getDouble(key) : 0d;
        } catch (Exception e) {
            return 0d;
        }
    }

    /**
     * 从JSONObject中获取对应的double
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the double
     */
    public static double getDouble(JSONObject jo, String key, double defaultValue) {
        try {
            return jo.has(key) ? jo.getDouble(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的JSONObject
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the JSONObject
     */
    public static JSONObject getJSONObject(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getJSONObject(key) : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从JSONObject中获取对应的JSONObject
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the JSONObject
     */
    public static JSONObject getJSONArray(JSONObject jo, String key, JSONObject defaultValue) {
        try {
            return jo.has(key) ? jo.getJSONObject(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 从JSONObject中获取对应的JSONArray
     *
     * @param jo  JSONObject对象
     * @param key the key
     * @return the JSONArray
     */
    public static JSONArray getJSONArray(JSONObject jo, String key) {
        try {
            return jo.has(key) ? jo.getJSONArray(key) : null;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 从JSONObject中获取对应的JSONArray
     *
     * @param jo           JSONObject对象
     * @param key          the key
     * @param defaultValue 返回的默认值
     * @return the JSONArray
     */
    public static JSONArray getJSONArray(JSONObject jo, String key, JSONArray defaultValue) {
        try {
            return jo.has(key) ? jo.getJSONArray(key) : defaultValue;
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 判断当前JSONArray是否为空
     *
     * @param ja JSONObject对象
     * @return boolean
     */
    public static boolean isEmpty(JSONArray ja) {
        return null == ja || ja.length() <= 0;
    }

    /**
     * 检查hashMap中的key是否含有有意义的value值
     *
     * @param hashMap HashMap 对象
     * @param key     the key
     * @return boolean
     */
    public static boolean hasSTRValue(HashMap<String, Object> hashMap, String key) {
        if (hashMap != null && hashMap.get(key) != null) {
            String value = hashMap.get(key).toString();
            if (!value.equals("") && !value.equals("null") && !value.equals("-")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取hashMap中的key的String类型value值
     *
     * @param hashMap HashMap 对象
     * @param key     the key
     * @return String
     */
    public static String getSTRValue(HashMap<String, Object> hashMap, String key) {
        if (hasSTRValue(hashMap, key)) {
            return hashMap.get(key).toString();
        }
        return "";
    }
}
