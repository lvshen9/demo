package com.lvshen.demo.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;

import java.util.Iterator;
import java.util.Set;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:45
 * @since JDK 1.8
 */
public class StringJsonUtils {
    public static String jsonFullStrTrim(String jsonStr) {
        boolean isJsonArray = isJSONArray(jsonStr);
        if (isJsonArray) {
            JSONArray parse = (JSONArray) JSONArray.parse(jsonStr);
            JSONArray jsonArrayAfterTrim = new JSONArray();
            for (Object o : parse) {
                boolean isJson = o instanceof JSONObject;
                if (isJson) {
                    String jsonString = JSONObject.toJSONString(o);
                    JSONObject jsonObject = jsonStrTrim(jsonString);
                    jsonArrayAfterTrim.add(jsonObject);
                } else if (o instanceof String) {
                    String oStr = (String) o;
                    String trim = oStr.trim();
                    jsonArrayAfterTrim.add(trim);
                } else {
                    return jsonStr;
                }
            }
            return jsonArrayAfterTrim.toJSONString();
        }
        return jsonStrTrim(jsonStr).toJSONString();
    }

    /**
     * json字符串首尾去空格
     *
     * @param jsonStr json字符串
     * @return 去掉首尾空格的json
     */
    public static JSONObject jsonStrTrim(String jsonStr) {
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        Set<String> keySet = jsonObject.keySet();
        Iterator<String> itt = keySet.iterator();
        while (itt.hasNext()) {
            String key = itt.next();
            Object obj = jsonObject.get(key);
            if (obj instanceof JSONObject) {
                Set<String> keySets = ((JSONObject) obj).keySet();
                Iterator<String> iterator = keySets.iterator();
                while (iterator.hasNext()) {
                    String key1 = iterator.next();
                    Object o = ((JSONObject) obj).get(key1);
                    if (o instanceof String) {
                        if (StringUtils.isNotBlank(o.toString().trim())) {
                            ((JSONObject) obj).put(key1, o.toString().trim());
                        }
                    }
                }
                jsonObject.put(key, obj);
            } else if (obj instanceof JSONArray) {
                for (int i = 0; i < ((JSONArray) obj).size(); i++) {
                    Object o = ((JSONArray) obj).get(i);
                    if (o instanceof JSONObject) {
                        Set<String> keySets = ((JSONObject) o).keySet();
                        Iterator<String> iterator = keySets.iterator();
                        while (iterator.hasNext()) {
                            String key1 = iterator.next();
                            Object o1 = ((JSONObject) o).get(key1);
                            if (o1 instanceof String) {
                                if (StringUtils.isNotBlank(o.toString().trim())) {
                                    ((JSONObject) o).put(key1, o1.toString().trim());
                                }
                            }
                        }
                    } else if (o instanceof String) {
                        if (StringUtils.isNotBlank(o.toString().trim())) {
                            ((JSONArray) obj).set(i, ((String) o).trim());
                        }
                    }
                }
                jsonObject.put(key, obj);
            } else if (obj instanceof String) {
                if (StringUtils.isNotBlank(obj.toString().trim())) {
                    jsonObject.put(key, obj.toString().trim());
                }
            }
        }
        return jsonObject;
    }

    /**
     * 检测字符串是否为json格式
     *
     * @param str
     * @return
     */
    public static boolean isJSON(String str) {
        boolean result;
        try {
            JSON obj = (JSON) JSON.parse(str);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }

    public static boolean isJSONArray(String str) {
        boolean result;
        try {
            JSONArray parse = (JSONArray) JSONArray.parse(str);
            result = true;
        } catch (Exception e) {
            result = false;
        }
        return result;
    }
}
