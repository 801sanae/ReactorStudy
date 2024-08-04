package com.kmy.study.mono;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

/*
 * Mono는 0개 또는 1개를 Emit
 * Http req / resp 사용하기 적합한 publisher 타입.
 */
public class MonoExample01 {

    public static void main(String[] args) {
        Mono.just(WebClient.create()
                        .get()
                        .uri(UriComponentsBuilder.newInstance().scheme("http")
                                .host("worldtimeapi.org")
                                .port(80)
                                .path("/api/timezone/Asia/Seoul")
                                .build()
                                .encode()
                                .toUri())
                        .retrieve()
                        .bodyToMono(String.class)
                        .block())
                .map(res -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        JsonNode jsonNode = objectMapper.readTree(res);
                        System.out.println(jsonNode.toString());
                        return jsonNode.path("datetime");
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .subscribe(
                        data -> {
                            System.out.println("onNext emit:: " + data);
                        },
                        error -> {
                            System.out.println("error :: " + error.getMessage());
                        },
                        () ->{
                            System.out.println("onComplete emit ");
                        }
                );
    }
}
