package com.spring.reactive.demo.webclient;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author yjj(Yoo Ju Jin)
 * Created 28/04/2019
 */

@Configuration
@ConfigurationProperties("web.server")
@Getter
@Setter
@Slf4j
public class WebClientConfig {
    private String ip;
    private Integer port;

    @Bean
    WebClient getWebClient() {
        log.debug("========== [server] ip: {}, port: {}", ip, port);
        return WebClient.builder().baseUrl("http://" + ip + ":" + port).build();
    }
}
