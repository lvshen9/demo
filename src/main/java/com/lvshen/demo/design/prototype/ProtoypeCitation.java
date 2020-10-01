package com.lvshen.demo.design.prototype;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import com.lvshen.demo.member.entity.Member;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Date;

/**
 * Description:原型模式，对象拷贝
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 13:55
 * @since JDK 1.8
 */
@Slf4j
public class ProtoypeCitation {

    @Test
    public void test() throws CloneNotSupportedException {

        Member member = new Member();
        member.setId("1");
        member.setName("Lvshen");
        Citation obj1 = new Citation("Lvshen", "同学：在2020学年第一学期中表现优秀，被评为三好学生。", "哈尔滨佛学院", member);
        System.out.println("1.调用obj1#display....");
        obj1.display();
        Citation obj2 = (Citation) obj1.clone();
        Member member2 = obj2.getMember();
        System.out.println("2.修改obj2中的数据");
        member2.setId("100");
        member2.setName("ZhouZhou");
        member2.setCode(101);
        System.out.println("3.调用obj2#display....");
        obj2.display();
        System.out.println("4.调用obj1#display....");
        obj1.display();
    }

    @Data
    class Citation implements Cloneable {
        String name;
        String info;
        String college;
        Date date;
        Member member;

        public Citation(String name, String info, String college, Member member) {
            this.name = name;
            this.info = info;
            this.college = college;
            this.member = member;

            log.info("Citation创建成功");
        }

        @Override
        public Object clone() throws CloneNotSupportedException {
            log.info("拷贝成功");
            return super.clone();
        }

        void display() {
            log.info(name + info + college + ",记录对象：" + member);
        }
    }
}
