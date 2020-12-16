package com.lvshen.demo.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Description: Json转List TypeHandler
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-12-16 10:04
 * @since JDK 1.8
 */
public class JsonTypeHandler<T> extends BaseTypeHandler<T> {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private Class<T> type;

    public JsonTypeHandler(Class<T> type) {
        if (type == null) {
            throw new NullPointerException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        ps.setString(i, toJsonString(parameter));
    }

    @Override
    public T getNullableResult(ResultSet rs, String columnName) throws SQLException {
        return parse(rs.getString(columnName));
    }


    @Override
    public T getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        return parse(rs.getString(columnIndex));
    }

    @Override
    public T getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        return parse(cs.getString(columnIndex));
    }

    private String toJsonString(Object parameter) {
        try {
            return objectMapper.writeValueAsString(parameter);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private T parse(String json) {
        try {
            if (json == null) {
                //避免空指针异常
                json = "[]";
            }
            //T t = objectMapper.readValue(json, type);
            return (T) list4Json(json, type);
            //return t;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

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

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }
}