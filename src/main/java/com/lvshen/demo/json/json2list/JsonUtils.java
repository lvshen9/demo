package com.lvshen.demo.json.json2list;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.gson.Gson;
import com.lvshen.demo.json.SurveyConclusionTemplateOptionDto;
import lombok.SneakyThrows;
import org.junit.Test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-17 9:16
 * @since JDK 1.8
 */
public class JsonUtils {

    public static <T> List<T> list4Json(String json, Class<T> typeclazz) {
        ParameterizedTypeImpl type = new ParameterizedTypeImpl(typeclazz);
        Gson gson = new Gson();
        return gson.fromJson(json, type);
    }

    private static class ParameterizedTypeImpl implements ParameterizedType {
        private Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }

    @Test
    public void testJson2List() {
        String json = "[{\"label\":\"result1\",\"name\":\"较好\",\"readonly\":false},{\"label\":\"result2\",\"name\":\"一般\",\"readonly\":false},{\"label\":\"result3\",\"name\":\"较差\",\"readonly\":false},{\"label\":\"result4\",\"name\":\"极差\",\"readonly\":false}]";
        List<SurveyConclusionTemplateOptionDto> templateOptionDtoList = list4Json(json, SurveyConclusionTemplateOptionDto.class);
        System.out.println(templateOptionDtoList);
    }

    @SneakyThrows
    public static String toJsonString(Object object) {
        return objectMapper.writeValueAsString(object);
    }
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将字符串转为对象
     */
    public static <T> T str2obj(String jsonStr, Class<T> cls) {
        try {
            return objectMapper.readValue(jsonStr, cls);
        } catch (Exception e) {
            throw Throwables.propagate(e);
        }
    }

}
