package com.lvshen.demo.annotation.datamask;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.AnnotationIntrospectorPair;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2022-7-22 9:21
 * @since JDK 1.8
 */
@Configuration(
        proxyBeanMethods = false
)
public class DataMaskConfiguration {
    @Configuration(
            proxyBeanMethods = false
    )
    @ConditionalOnClass({Jackson2ObjectMapperBuilder.class})
    static class JacksonObjectMapperConfiguration {
        JacksonObjectMapperConfiguration() {
        }

        @Bean
        @Primary
        ObjectMapper jacksonObjectMapper(Jackson2ObjectMapperBuilder builder) {
            ObjectMapper objectMapper = builder.createXmlMapper(false).build();
            AnnotationIntrospector ai = objectMapper.getSerializationConfig().getAnnotationIntrospector();
            AnnotationIntrospector newAi = AnnotationIntrospectorPair.pair(ai, new DataMaskingAnnotationIntrospector());
            objectMapper.setAnnotationIntrospector(newAi);
            return objectMapper;
        }
    }
}
