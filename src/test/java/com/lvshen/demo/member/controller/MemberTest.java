package com.lvshen.demo.member.controller;

import com.lvshen.demo.RedisSpringTest;
import com.lvshen.demo.arithmetic.snowflake.SnowFlakeGenerator;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.entity.vo.OrderVo;
import com.lvshen.demo.member.service.MemberService;
import com.lvshen.demo.member.service.OrderInfoService;
import com.lvshen.demo.treenode.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.junit4.SpringRunner;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import static com.lvshen.demo.RedisSpringTest.GET_NEXT_CODE;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 16:31
 * @since JDK 1.8
 */

@Slf4j
@SpringBootTest
@RunWith(SpringRunner.class)
class MemberTest {

    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberService memberService;

    @Autowired
    private OrderInfoService orderInfoService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Test
    void deleteById() {
    }

    @Test
    public void testOrderAnnotation() {
        List<OrderVo> orderVos = orderInfoService.listOrderVoByAnnotation();
        System.out.println("展示的vo为：" + orderVos);
    }

    @Test
    public void testOrder() {
        List<OrderVo> orderVos = orderInfoService.listOrderVo();
        log.info("Test is Over!!!");
    }

    @Test
    void update() {
    }

    @Test
    void insert() throws ParseException {
        Member member = new Member();
        String id = new SnowFlakeGenerator().nextId();
        member.setId(id);
        long code = new RedisSpringTest().getCode(GET_NEXT_CODE);
        member.setCode((int)code);
        member.setName("zhouzhou");

        Member insert = memberController.insert(member);
        log.info("数据插入成功: {}",insert);
    }

    @Test
    void listMember() {
        List<Member> members = memberController.listMember();
        log.info("members:{}",members);
    }

    @Test
    void listByName() {
        String name = "zhouzhou";
        List<Member> members = memberController.listByName(name);
        log.info("members:{}",members);
    }

    @Test
    void testCache() {
        String name = "lvshen99";
        List<Member> members = memberService.listByNameSelfCache(name);
        log.info("members:{}",members);
    }

    @Test
    public void sendSimpleMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setSubject("这是一封测试邮件-Lvshen的技术小屋！！！");
        message.setFrom("1587023453@qq.com");
        message.setTo("2621555149@qq.com");
        message.setSentDate(new Date());
        message.setText("欢迎关注Lvshen的技术小屋！！！");
        javaMailSender.send(message);
        System.out.println("发送成功！！！");
    }

    @Test
    public void sendAttachFileMail() throws Exception {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true);
        helper.setSubject("这是一封测试邮件-测试附件");
        helper.setFrom("1587023453@qq.com");
        helper.setTo("2621555149@qq.com");
        helper.setSentDate(new Date());
        helper.setText("欢迎关注Lvshen的技术小屋，附件是二维码哦！！！");
        helper.addAttachment("Lvshen的技术小屋.jpg",new File("E:\\Lvshen的技术小屋.jpg"));
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void sendImgResMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是一封测试邮件-正文带图片哦");
        helper.setFrom("1587023453@qq.com");
        helper.setTo("2621555149@qq.com");
        helper.setSentDate(new Date());
        helper.setText("<p>嗨 这里是邮件测试哦，正文包含图片，分别如下</p><p>第一张图片为我的公众号二维码：</p><img src='cid:p01'/><p>第二张图片为我搭建的个人博客：</p><img src='cid:p02'/>",true);
        helper.addInline("p01",new FileSystemResource(new File("E:\\Lvshen的技术小屋.jpg")));
        helper.addInline("p02",new FileSystemResource(new File("E:\\博客.png")));
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void sendThymeleafMail() throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setSubject("这是一封测试邮件-模板引擎");
        helper.setFrom("1587023453@qq.com");
        helper.setTo("2621555149@qq.com");
        helper.setSentDate(new Date());
        Context context = new Context();
        context.setVariable("username", "Lvshen");
        context.setVariable("sex","male");
        context.setVariable("hobby", "Java");
        String process = templateEngine.process("thymeleaf-mail.html", context);
        helper.setText(process,true);
        javaMailSender.send(mimeMessage);
    }

    @Test
    public void test9() {
        Student student = new Student();
        student.setName("Lvshen");
        student.setScore(100);
        student.setDate(new Date());
        System.out.println(student);
    }
}