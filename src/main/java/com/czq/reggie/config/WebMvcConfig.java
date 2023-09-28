package com.czq.reggie.config;

import com.czq.reggie.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Slf4j
public class WebMvcConfig {
    @Bean
    public WebMvcConfigurer enableMatrixVariable() {
        return new WebMvcConfigurer() {
            /**
             * 扩展SpringMvc的消息转换器
             *
             * @param converters
             */
            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                log.info("自定义消息转化器 被调用!");
                // 创建消息转换器对象
                MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
                // 设置对象转换器，底层使用JackSON 将Java对象 转化为JSON
                messageConverter.setObjectMapper(new JacksonObjectMapper());

                // 将上面的消息转换器对象追加到SpringMVC的 转换器容器 的第一个位置(优先采用下标为 0 位置的消息转换器)
                converters.add(0, messageConverter);
            }
        };
    }
}
