package com.lvshen.demo.autoidempotent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 21:30
 * @since JDK 1.8
 */

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class BusinessController {


    @Resource
    private TokenService tokenService;

    @Autowired
    private TestService testService;

    private static final String CODE_SUCCESS = "success";


    @PostMapping("/get/token")
    public String getToken() {
        String token = tokenService.createToken();
        if (StringUtils.isNotEmpty(token)) {
            ResultVo resultVo = new ResultVo();
            resultVo.setCode("100");
            resultVo.setMessage(CODE_SUCCESS);
            resultVo.setData(token);
            return JSONObject.toJSONString(resultVo);
        }
        return StringUtils.EMPTY;
    }


    @AutoIdempotent
    @PostMapping("/test/Idempotence")
    public ServerResponse testIdempotence() {
        /*String businessResult = testService.testIdempotence();
        if (StringUtils.isNotEmpty(businessResult)) {
            ResultVo successResult = ResultVo.getSuccessResult(businessResult);
            return JSONUtil.toJsonStr(successResult);
        }*/
        return testService.testIdempotence();
    }
}

