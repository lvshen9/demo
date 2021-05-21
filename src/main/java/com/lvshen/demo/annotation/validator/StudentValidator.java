package com.lvshen.demo.annotation.validator;


import cn.hutool.core.date.DateUtil;
import com.lvshen.demo.catchexception.BusinessExceptionAssert;
import com.lvshen.demo.treenode.Student;

import java.util.Date;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021-5-20 17:09
 * @since JDK 1.8
 */
public class StudentValidator implements AbstractValidator {
    private static final int MIN_SCORE = 90;

    private static final String REGISTER_DATE_STR = "2019-03-01";

    @Override
    public void check(Object o) {
        Student student = (Student) o;
        BusinessExceptionAssert.checkNotNull(student, "参数不能为空");
        Integer score = student.getScore();
        BusinessExceptionAssert.checkArgument(score > MIN_SCORE || score == MIN_SCORE, "成绩需要大于或等于才能满足要求");
        Date date = student.getDate();
        Date registerDate = DateUtil.parse(REGISTER_DATE_STR);
        BusinessExceptionAssert.checkArgument(date.after(registerDate), String.format("注册时间【%s】不能小于【%s】", DateUtil.formatDate(date), DateUtil.formatDate(registerDate)));
    }
}
