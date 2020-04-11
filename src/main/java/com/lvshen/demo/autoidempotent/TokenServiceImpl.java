package com.lvshen.demo.autoidempotent;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020/4/10 21:01
 * @since JDK 1.8
 */

import org.apache.commons.lang.text.StrBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private RedisTemplate redisTemplate;

    private final static String TOKEN_PREFIX = "redis_token_prefix_";

    private static final String HEADER_KEY = "token";


    /**
     * 创建token
     *
     * @return
     */
    @Override
    public String createToken() {
        String str = UUID.randomUUID().toString();
        StrBuilder token = new StrBuilder();
        try {
            token.append(TOKEN_PREFIX).append(str);
            redisTemplate.opsForValue().setIfAbsent(token.toString(),token.toString(),10000L, TimeUnit.SECONDS);
            //redisService.setEx(token.toString(), token.toString(), 10000L);
            boolean notEmpty = StringUtils.isNotEmpty(token.toString());
            if (notEmpty) {
                return token.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }


    /**
     * 检验token
     *
     * @param request
     * @return
     */
    @Override
    public boolean checkToken(HttpServletRequest request) throws Exception {

        String token = request.getHeader(HEADER_KEY);
        if (StringUtils.isBlank(token)) {// header中不存在token
            token = request.getParameter(HEADER_KEY);
            if (StringUtils.isBlank(token)) {// parameter中也不存在token
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }

        if (!redisTemplate.hasKey(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }

        boolean remove = redisTemplate.delete(token);
        if (!remove) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        return true;
    }
}

