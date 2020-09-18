package com.lvshen.demo.member.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Snowflake;
import com.lvshen.demo.member.entity.ContinueSign;
import com.lvshen.demo.member.entity.Sign;
import com.lvshen.demo.member.mapper.ContinueSignMapper;
import com.lvshen.demo.member.mapper.SignMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/7 15:57
 * @since JDK 1.8
 */
@Service
public class SignService {
    @Autowired
    private SignMapper signMapper;

    @Autowired
    private ContinueSignMapper continueSignMapper;

    @Transactional(rollbackFor = Exception.class)
    public int createSign(String userId) {
        String id = generateId();

        Date todayDate = new Date();

        Sign sign = new Sign();
        sign.setId(id);
        sign.setSignDate(todayDate);
        sign.setUserId(userId);

        //获取最近的签到日期
        Date maxSignDate = signMapper.getMaxDateByUserId(userId);
        if (maxSignDate != null) {
            if (DateUtil.isSameDay(todayDate, maxSignDate)) {
                throw new RuntimeException(String.format("今天已经签到了哦，最近签到日期：【%s】", maxSignDate));
            }
        }
        int i = signMapper.insert(sign);

        //更新连续签到次数
        updateContinueSignCounts(userId);

        List<ContinueSign> continueSignList = continueSignMapper.listByUserId(userId);
        if (CollectionUtils.isNotEmpty(continueSignList)) {
            ContinueSign continueSign = continueSignList.get(0);
            Integer continueCounts = continueSign.getContinueCounts();
            if (continueCounts >=7) {
                //发放优惠卷
            }
        }

        return i;
    }

    private void updateContinueSignCounts(String userId) {
        List<ContinueSign> continueSignList = continueSignMapper.listByUserId(userId);
        if (CollectionUtils.isEmpty(continueSignList)) {
            ContinueSign continueSign = new ContinueSign();
            String generateId = generateId();
            continueSign.setId(generateId);
            continueSign.setUserId(userId);
            continueSign.setContinueCounts(1);
            continueSignMapper.insert(continueSign);
        } else {
            continueSignMapper.addSignCountsById(userId);
        }
    }

    private String generateId() {
        Snowflake snowflake = new Snowflake(1, 2);
        long l = snowflake.nextId();
        return String.valueOf(l);
    }


}
