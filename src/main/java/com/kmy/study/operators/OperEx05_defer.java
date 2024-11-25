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
 *
 *
 * Mono.just()는 hot publisher 이므로, subscrible과 별개로 데이터를 emitt하지만
 * Mono.defer(()-> ~~ ) , .subscirble을 해야 데이터가 emitt된다.
 */
@Slf4j
public class OperEx05_defer {

    public static void main(String[] args) throws InterruptedException{
        log.info("#1 start : {}", LocalDateTime.now());

        // #1 Mono.just() 자체는 hot Publisher이기 떄뭔에 subscirble과 관련없이 데이터를 emit한다.
        Mono<LocalDateTime> justMono = Mono.just(LocalDateTime.now()); //2024-11-26T00:01:38.879875

        Mono<LocalDateTime> deferMono = Mono.defer(() -> Mono.just(LocalDateTime.now()));

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just1 Mono : {}", data)); // 2024-11-26T00:01:38.886018

        // #2 Mono.defer()는 subscribe 시점에 Mono.just()가 emit하는 데이터를 emit한다.
        deferMono.subscribe(data -> log.info("# onNext defer1 Mono : {}", data)); // 2024-11-26T00:01:40.941082

        Thread.sleep(2000);

        justMono.subscribe(data -> log.info("# onNext just2 Mono : {}", data)); // 2024-11-26T00:01:38.886018
        deferMono.subscribe(data -> log.info("# onNext defer2 Mono : {}", data)); //2024-11-26T00:01:42.946476

//      ==> 2초 간격을 두고, subscrible이 되야 데이터가 emitted된걸 알수 있다.

//        00:01:38.881 [main] INFO com.kmy.study.operators.OperEx05_defer -- #1 start : 2024-11-26T00:01:38.879875
//        00:01:40.940 [main] INFO com.kmy.study.operators.OperEx05_defer -- # onNext just1 Mono : 2024-11-26T00:01:38.886018
//        00:01:40.941 [main] INFO com.kmy.study.operators.OperEx05_defer -- # onNext defer1 Mono : 2024-11-26T00:01:40.941082
//        00:01:42.945 [main] INFO com.kmy.study.operators.OperEx05_defer -- # onNext just2 Mono : 2024-11-26T00:01:38.886018
//        00:01:42.946 [main] INFO com.kmy.study.operators.OperEx05_defer -- # onNext defer2 Mono : 2024-11-26T00:01:42.946476

        log.info("#2 start : {}", LocalDateTime.now());

        Mono.just("hello")
                .delayElement(Duration.ofSeconds(3))
//                .switchIfEmpty(sayDefault())
                .switchIfEmpty(Mono.defer(()->sayDefault()))
                .subscribe(data -> log.info("# onNext: {}", data));

        Thread.sleep(2000);
    }

    private static Mono<String> sayDefault() {
        log.info("# Say Hey");
        return Mono.just("Hi");
    }
}
