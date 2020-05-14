package com.lvshen.demo.redis.session.controller;

import com.alibaba.fastjson.JSON;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/5/14 21:03
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/session",method = {RequestMethod.GET, RequestMethod.POST})
public class RequestionSessionController {
    @Autowired
    MemberService memberService;

    @RequestMapping(value = "/testSession",method = RequestMethod.GET)
    public String testSession(HttpSession session, Model model) {
        List<Member> members = memberService.listMember();
        System.out.println("sessionId------>" + session.getId());
        model.addAttribute("member", JSON.toJSONString(members));
        session.setAttribute("member",JSON.toJSONString(members));
        return "hello world";
    }
}
