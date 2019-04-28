package com.spring.reactive.demo;

import com.spring.reactive.demo.common.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class WebClientTests {
    @Autowired
    WebClient webClient;

    @Value("${web.server.ip}")
    private String serverIp;
    @Value("${web.server.port}")
    private Integer serverPort;

    String uri = "/userinfo";

    @Test
    public void contextLoads() {
        Assert.assertNotNull(webClient);
        log.debug("=================== webClient : {}", webClient);
    }

    /*
     * syncBody(Object)
     * body(BodyInserters.fromObject(Object)
     * body(Flux<Object>, Object.class)
     * 모두 같음.
     */
    @Test
    public void syncBodyTest() {
        List<UserInfo> userInfoList = getUserInfoList();
        log.info("userInfoList : {}", userInfoList);

        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://" + serverIp + ":" + serverPort).build();
        webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .syncBody(userInfoList)
                .exchange()
                .expectBody(Void.class)
                .consumeWith(result -> {
                    log.debug("====== responseCode : {}", result.getStatus());
                });
    }

    @Test
    public void bodyInserterTest() {
        List<UserInfo> userInfoList = getUserInfoList();
        log.info("ecoDrvDataList : {}", userInfoList);

        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://" + serverIp + ":" + serverPort).build();
        webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(userInfoList))
                .exchange()
                .expectBody(Void.class)
                .consumeWith(result -> {
                    log.debug("====== responseCode : {}", result.getStatus());
                });
    }

    @Test
    public void fluxBodyTest() {
        List<UserInfo> userInfoList = getUserInfoList();
        Flux<UserInfo> reqBody = Flux.fromIterable(userInfoList);
        WebTestClient webTestClient = WebTestClient.bindToServer().baseUrl("http://" + serverIp + ":" + serverPort).build();
        webTestClient.post()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(reqBody, UserInfo.class)
                .exchange()
                .expectBody(Void.class)
                .consumeWith(result -> {
                    log.debug("====== responseCode : {}", result.getStatus());
                });
    }

    private List<UserInfo> getUserInfoList() {
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(UserInfo.builder().id("hongGold").userName("홍길금").email("gold@gmail.com").build());
        userInfoList.add(UserInfo.builder().id("hongSilver").userName("홍길은").email("silver@gmail.com").build());
        userInfoList.add(UserInfo.builder().id("hongBronz").userName("홍길동").email("dong@gmail.com").build());

        return userInfoList;
    }
}
