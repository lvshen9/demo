package com.lvshen.demo.arithmetic.tradno;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

/**
 * Description:
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/7/18 14:57
 * @since JDK 1.8
 */
public class NetUtils {
    private static final Logger logger = LoggerFactory.getLogger(NetUtils.class);

    private NetUtils() {
    }
    /**
     * post请求
     *
     * @param url
     * @param param
     * @param postContext
     * @param headers
     * @param timeoutMills 超时时间（毫秒）
     * @return
     */
    public static String sendPost(String url, String param, String postContext, Map<String, String> headers, int timeoutMills) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String responseBody = null;

        String realUrl = url;

        if (!StringUtils.isEmpty(param)) {
            realUrl = url + "?" + param;
        }

        try {
            HttpPost httpPost = new HttpPost(realUrl);

            if (timeoutMills > 0) {
                //设置请求和传输超时时间
                RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(timeoutMills).setConnectTimeout(timeoutMills).setConnectionRequestTimeout(timeoutMills).build();
                httpPost.setConfig(requestConfig);
            }

            addHeaders(httpPost, headers);

            httpPost.setEntity(new StringEntity(postContext, "UTF-8"));
            // 构建响应处理器
            ResponseHandler<String> responseHandler = response -> {
                int status = response.getStatusLine().getStatusCode();
                if (status >= 200 && status < 300) {
                    HttpEntity entity = response.getEntity();
                    return entity != null ? EntityUtils.toString(entity) : null;
                } else {
                    throw new ClientProtocolException("Unexpected response status: " + status);
                }
            };

            responseBody = httpclient.execute(httpPost, responseHandler);
        } catch (Exception ex) {
            logger.error("发送POST请求失败：url: " + url + " param: " + param + " postContext:" + postContext, ex);
        } finally {
            try {
                httpclient.close();
            } catch (IOException e) {
                logger.error("关闭httpclient失败：url: " + url + " param: " + param + " postContext:" + postContext, e);
            }
        }

        return responseBody;
    }

    private static void addHeaders(HttpRequestBase httpMethod, Map<String, String> headers) {
        if (headers != null) {
            Iterator<Map.Entry<String, String>> resultIt = headers.entrySet().iterator();
            while (resultIt.hasNext()) {
                Map.Entry<String, String> entry = resultIt.next();
                httpMethod.setHeader(entry.getKey(), entry.getValue());
            }
        }
    }
}
