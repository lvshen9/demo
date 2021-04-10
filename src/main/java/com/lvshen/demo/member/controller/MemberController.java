package com.lvshen.demo.member.controller;

import com.lvshen.demo.annotation.log.OperationLog;
import com.lvshen.demo.annotation.log.OperationType;
import com.lvshen.demo.annotation.sensitive.SensitiveService;
import com.lvshen.demo.annotation.sensitive.UserInfo;
import com.lvshen.demo.member.entity.Member;
import com.lvshen.demo.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 16:04
 * @since JDK 1.8
 */
@RestController
@RequestMapping(value = "/member", method = {RequestMethod.GET, RequestMethod.POST})
public class MemberController {
    @Autowired
    private MemberService memberService;

    @Autowired
    private SensitiveService sensitiveService;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
    public UserInfo getUserInfo() {
        return sensitiveService.getUserInfo();
    }

    @RequestMapping(value = "/delete", method = RequestMethod.GET)
    public int deleteById(String id) {
        return memberService.deleteById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(Member member) {
        return memberService.updateMember(member);
    }

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public Member insert(Member user) {
        return memberService.createMember(user);
    }

    @RequestMapping("/listMember")
    @ResponseBody
    public List<Member> listMember() {
        return memberService.listMember();
    }

    @RequestMapping("/listByName")
    @ResponseBody
    public List<Member> listByName(String name) {
        return memberService.listByName(name);
    }

    @RequestMapping("/getById")
    @ResponseBody
    @OperationLog(opType = OperationType.QUERY,opBusinessName = "会员服务",opBusinessId = "#id")
    public Member getById(@RequestParam String id) {
        return memberService.getById(id);
    }


}
