package com.lvshen.demo.mapstruct;

import lombok.Data;
import lombok.ToString;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-21 16:02
 * @since JDK 1.8
 */
@Data
public class StudentDto {
    private String id;
    private String code;
    private String sex;
    private String userName;

    @Override
    public String toString() {
        return "StudentDto{" +
                "id='" + id + '\'' +
                ", code='" + code + '\'' +
                ", sex='" + sex + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
