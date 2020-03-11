package com.lvshen.demo.springcloudalibaba;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2020/3/11 12:41
 * @since JDK 1.8
 */

@Controller
@RequestMapping("config")
@RefreshScope
public class TestNacosConfigController {

    @Value("${useLocalCache:false}")
    private boolean useLocalCache;

    @Value("${name:zhouzhou}")
    private String name;

    public void setUseLocalCache(boolean useLocalCache) {
        this.useLocalCache = useLocalCache;
    }

    @RequestMapping(value = "/get", method = GET)
    @ResponseBody
    public boolean get() {
        return useLocalCache;
    }

    @RequestMapping(value = "/getName", method = GET)
    @ResponseBody
    public String getName() {
        return name;
    }
}
