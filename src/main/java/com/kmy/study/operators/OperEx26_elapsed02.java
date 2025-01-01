package com.kmy.study.operators;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.util.function.Tuples;
import reactor.util.retry.Retry;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;

/**
 * elapsed를 쓰면 투플로 millisecond의 시간을 측정할 수 있다.
 */
@Slf4j
public class OperEx26_elapsed02 {

    public static void main(String[] args) throws JsonProcessingException {
        URI worldTimeUri = UriComponentsBuilder.newInstance().scheme("https")
                .host("worldtimeapi.org")
                .port(80)
                .path("/api/timezone/Asia/Seoul")
                .build()
                .encode()
                .toUri();

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String worldTime1 = WebClient.create()
                .get()
                .uri("https://worldtimeapi.org/api/timezone/Asia/Seoul")
                .retrieve()
                .bodyToMono(String.class)
                .block();

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();

        String worldTime2 = webClient
                .get()
                .uri("https://worldtimeapi.org/api/timezone/Asia/Seoul")
                .retrieve()
                .bodyToMono(String.class)
                .doOnError(error -> {
                    System.err.println("Error occurred: " + error.getMessage());
                })
                .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(2)))
                .block();

        log.info("worldTime1 : {}", worldTime1);
        log.info("worldTime2 : {}", worldTime2);

        Mono.defer(()-> Mono.just(rt.exchange(worldTimeUri, HttpMethod.GET, new HttpEntity<String>(headers),String.class)))
                .repeat(4)
                .elapsed()
                .map(res -> {
//
//                    res.getT2().getBody();
                    log.info("res.getT2().getBody() : {}", res.getT2().getBody());
                    return Tuples.of(LocalDateTime.now(), res.getT1());
                })
                .subscribe(data -> log.info("now : {}, elpased : {}" , data.getT1(), data.getT2()),
                        error -> log.error("onError ::",error),
                        ()->log.info("onComplete"));
//
    }
}
