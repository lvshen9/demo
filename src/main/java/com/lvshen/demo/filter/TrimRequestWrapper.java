package com.lvshen.demo.filter;

import com.google.common.collect.Maps;
import com.lvshen.demo.annotation.easyexport.utils.PropertiesUtil;
import com.lvshen.demo.common.MyStringUtils;
import com.lvshen.demo.json.json2list.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-12-20 15:42
 * @since JDK 1.8
 */
@Slf4j
public class TrimRequestWrapper extends HttpServletRequestWrapper {
    /**
     * 保存处理后的参数
     */
    private Map<String, String[]> params = Maps.newConcurrentMap();

    private static boolean NEED_FILTER = true;

    public TrimRequestWrapper(HttpServletRequest request) {
        /**
         * 将request交给父类，以便于调用对应方法的时候，将其输出
         */
        super(request);

        NEED_FILTER = !isMatch(request);

        /**
         * 对于非json请求的参数进行处理
         */
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        boolean nullType = header == null;
        boolean notJson = nullType || !header.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE);
        boolean notJsonUtf8 = nullType || !header.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE);
        boolean needParam = nullType || ((notJson) && notJsonUtf8);
        Map<String, String[]> parameterMap = request.getParameterMap();
        if (needParam || MapUtils.isNotEmpty(parameterMap)) {
            setParams(request);
        }
    }

    private boolean isMatch(HttpServletRequest request) {
        String requestUri = request.getRequestURI();
        boolean isMatch = false;
        //获取需要排除的url
        String excludeUrlsStr = PropertiesUtil.getProperty("basic-service.filter.trim.exclude-urls");
        List<String> excludeUrlList = MyStringUtils.string2List(excludeUrlsStr, ";");

        if (CollectionUtils.isNotEmpty(excludeUrlList)) {
            for (String exclude : excludeUrlList) {
                exclude = exclude.replace("/*", "/");
                List<String> requestSplit = MyStringUtils.string2List(requestUri, "/");
                List<String> excludeSplit = MyStringUtils.string2List(exclude, "/");
                for (String excludeElement : excludeSplit) {
                    int currentIndex = requestSplit.indexOf(excludeElement);
                    isMatch = currentIndex != -1;
                }
                if (isMatch) {
                    return true;
                }
            }
        }
        return false;
    }

    private void setParams(HttpServletRequest request) {
        //将请求的的参数转换为map集合
        Map<String, String[]> requestMap = request.getParameterMap();
        this.params.putAll(requestMap);
        //去空操作
        if (NEED_FILTER) {
            try {
                if (MapUtils.isNotEmpty(requestMap)) {
                    log.info("kv转化前参数：" + JsonUtils.toJsonString(requestMap));
                    this.modifyParameterValues();
                    log.info("kv转化后参数：" + JsonUtils.toJsonString(params));
                }
            } catch (Exception e) {
                log.error("param类型参数去空格转换异常，按原参数返回：", e);
            }
        }
    }

    /**
     * 将parameter的值去除空格后重写回去
     */
    public void modifyParameterValues() {
        Set<String> set = params.keySet();
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            String key = it.next();
            String[] values = params.get(key);
            values[0] = values[0].trim();
            params.put(key, values);
        }
    }

    /**
     * 重写getParameter 参数从当前类中的map获取
     */
    @Override
    public String getParameter(String name) {
        String[] values = params.get(name);
        if (values == null || values.length == 0) {
            return null;
        }
        return values[0];
    }

    /**
     * 重写getParameterValues
     */
    @Override
    public String[] getParameterValues(String name) {
        return params.get(name);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        String header = super.getHeader(HttpHeaders.CONTENT_TYPE);
        boolean nullHeader = header == null;
        //
        if (nullHeader || (!header.equalsIgnoreCase(MediaType.APPLICATION_JSON_VALUE) &&
                !header.equalsIgnoreCase(MediaType.APPLICATION_JSON_UTF8_VALUE))) {
            //如果参数不是json格式则直接返回
            return super.getInputStream();
        }
        //为空，直接返回
        String json = IOUtils.toString(super.getInputStream(), String.valueOf(StandardCharsets.UTF_8));
        if (StringUtils.isEmpty(json)) {
            return super.getInputStream();
        }

        //json字符串首尾去空格
        if (NEED_FILTER) {
            try {
                log.info("json转化前参数：{}", json);
                json = StringJsonUtils.jsonFullStrTrim(json);
                log.info("json转化后参数：{}", json);
            } catch (Exception e) {
                log.error("json转化异常，按原参数返回：", e);
            }
        }
        ByteArrayInputStream bis = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8));
        return new ScServletInputStream(bis);
    }


}
