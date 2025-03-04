package com.kmy.study.mono;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuples;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * packageName    : com.kmy.study.mono
 * fileName       : MonoExample02_expendDeep
 * author         : kmy
 * date           : 3/4/25
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 3/4/25        kmy       최초 생성
 */

/**
 * expendDeep 은 재귀적으로 호출된다.
 * 주의 사항은 무한루프에 빠지지 않게 조건부여 및 체크가 필수.
 */
@Slf4j
public class MonoExample02_expendDeep {


    public static void main(String[] args) {

        // #1 조건으로 재귀횟수 제한
        Mono.just("Hello Reactor")
            .expandDeep(data ->
                data.length() > 50 ? Mono.empty() : Mono.just(data + " -> " + data)
            )
            .subscribe(data -> log.info("# onNext() : {}", data));


        // #2-1 재귀횟수 카운트
        AtomicInteger counter = new AtomicInteger(0);

        Mono.just("Hello Reactor")
                .expandDeep(data -> {
                    int count = counter.incrementAndGet();
                    log.info("재귀 호출 횟수: {}", count);
                    return data.length() > 50 ? Mono.empty() : Mono.just(data + " -> " + data);
                })
                .subscribe(data -> log.info("# onNext() : {}", data));


        // #2-2 튜플을 이용한 재귀횟수 제한
        int maxRecursion = 5;

        Mono.just(Tuples.of("Hello Reactor", 0))
                .expandDeep(tuple -> {
                    String data = tuple.getT1();
                    int count = tuple.getT2() + 1;
                    if (count > maxRecursion || data.length() > 50) {
                        return Mono.empty();
                    }
                    return Mono.just(Tuples.of(data + " -> " + data, count));
                })
                .subscribe(data -> log.info("# onNext() : {}\n재귀 호출 횟수 : {}", data.getT1(), data.getT2()));
    }
}
