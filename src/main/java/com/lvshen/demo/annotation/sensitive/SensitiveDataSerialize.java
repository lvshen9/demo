package com.lvshen.demo.annotation.sensitive;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import com.lvshen.demo.annotation.sensitive.ruleenginee.RuleService;
import com.lvshen.demo.annotation.sensitive.strategy.SensitiveStrategyService;

import java.io.IOException;
import java.util.Objects;

import static com.lvshen.demo.annotation.sensitive.SensitiveType.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2021/4/10 11:24
 * @since JDK 1.8
 */
public class SensitiveDataSerialize extends JsonSerializer<String> implements
        ContextualSerializer {
    private SensitiveType type;

    public SensitiveDataSerialize() {
    }

    public SensitiveDataSerialize(final SensitiveType type) {
        this.type = type;
    }

    @Override
    public void serialize(final String s, final JsonGenerator jsonGenerator,
                          final SerializerProvider serializerProvider) throws IOException {
        //jsonHandler(s, jsonGenerator);

        SensitiveStrategyService sensitiveStrategyService = SpringContextHolder.getBean(SensitiveStrategyService.class);
        //String generatorSensitive = sensitiveStrategyService.generatorSensitive(this.type, s);

        RuleService ruleService = SpringContextHolder.getBean(RuleService.class);
        String generatorSensitive = ruleService.execute(this.type, s);

        jsonGenerator.writeString(generatorSensitive);
    }

    /**
     * 使用switch
     * @param s
     * @param jsonGenerator
     * @throws IOException
     */
    private void jsonHandler(String s, JsonGenerator jsonGenerator) throws IOException {
        switch (this.type) {
            case CHINESE_NAME: {
                jsonGenerator.writeString(SensitiveInfoUtils.chineseName(s));
                break;
            }
            case ID_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtils.idCardNum(s));
                break;
            }
            case FIXED_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtils.fixedPhone(s));
                break;
            }
            case MOBILE_PHONE: {
                jsonGenerator.writeString(SensitiveInfoUtils.mobilePhone(s));
                break;
            }
            case ADDRESS: {
                jsonGenerator.writeString(SensitiveInfoUtils.address(s, 4));
                break;
            }
            case EMAIL: {
                jsonGenerator.writeString(SensitiveInfoUtils.email(s));
                break;
            }
            case BANK_CARD: {
                jsonGenerator.writeString(SensitiveInfoUtils.bankCard(s));
                break;
            }
        }
    }

    /**
     * 使用if
     * @param s
     * @param jsonGenerator
     * @throws IOException
     */
    private void jsonHandlerWithIf(String s, JsonGenerator jsonGenerator) throws IOException {
        if (CHINESE_NAME == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.chineseName(s));
        }
        if (ID_CARD == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.idCardNum(s));
        }
        if (FIXED_PHONE == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.fixedPhone(s));
        }
        if (MOBILE_PHONE == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.mobilePhone(s));
        }
        if (ADDRESS == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.address(s, 4));
        }
        if (EMAIL == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.email(s));
        }
        if (BANK_CARD == this.type) {
            jsonGenerator.writeString(SensitiveInfoUtils.bankCard(s));
        }
    }

    @Override
    public JsonSerializer<?> createContextual(final SerializerProvider serializerProvider,
                                              final BeanProperty beanProperty) throws JsonMappingException {
        // 为空直接跳过
        if (beanProperty != null) {
            // 非 String 类直接跳过
            if (Objects.equals(beanProperty.getType().getRawClass(), String.class)) {
                SensitiveInfo sensitiveInfo = beanProperty.getAnnotation(SensitiveInfo.class);
                if (sensitiveInfo == null) {
                    sensitiveInfo = beanProperty.getContextAnnotation(SensitiveInfo.class);
                }
                // 如果能得到注解，就将注解的 value 传入 SensitiveInfoSerialize
                if (sensitiveInfo != null) {
                    return new SensitiveDataSerialize(sensitiveInfo.value());
                }
            }
            return serializerProvider.findValueSerializer(beanProperty.getType(), beanProperty);
        }
        return serializerProvider.findNullValueSerializer(null);

    }
}
