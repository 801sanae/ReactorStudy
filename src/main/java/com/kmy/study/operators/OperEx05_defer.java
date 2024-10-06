package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * packageName    : com.kmy.study.operators
 * fileName       : OperEx05_defer
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 *
 * Mono.defer()
 * subscribe 시점에 데이터를 emit한다.
 */
@Slf4j
public class OperEx05_defer {

    public static void main(String[] args) throws InterruptedException{
        log.info("#1 start : {}", LocalDateTime.now());

        // #1 Mono.just() 자체는 hot Publisher이기 떄뭔에 subscirble과 관련없이 데이터를 emit한다.
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now());

        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just1 Mono : {}", data));

        // #2 Mono.defer()는 subscribe 시점에 Mono.just()가 emit하는 데이터를 emit한다.
        deferMono.subscribe(data -> log.info("# onNext defer1 Mono : {}", data));

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just2 Mono : {}", data));
        deferMono.subscribe(data -> log.info("# onNext defer2 Mono : {}", data));

        log.info("#2 start : {}", LocalDateTime.now());

        Mono.just("hello")
                .delayElement(Duration.ofSeconds(3))
                .switchIfEmpty(sayDefault())
//                .switchIfEmpty(Mono.defer(()->sayDefault()))
                .subscribe(data -> log.info("# onNext: {}", data));
    }

    private static Mono<String> sayDefault() {
        log.info("# Say Hey");
        return Mono.just("Hi");
    }
}
