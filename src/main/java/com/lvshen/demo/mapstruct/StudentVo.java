package com.lvshen.demo.mapstruct;


/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-8-21 16:08
 * @since JDK 1.8
 */
public class StudentVo {
    private String id;
    private String name;
    private String code;
    private String age;
    private String score;
    private String sex;

    public StudentVo(String id, String name, String code, String age, String score, String sex) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.age = age;
        this.score = score;
        this.sex = sex;
    }

    public StudentVo() {
    }

    @Override
    public String toString() {
        return "StudentVo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", age='" + age + '\'' +
                ", score='" + score + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
