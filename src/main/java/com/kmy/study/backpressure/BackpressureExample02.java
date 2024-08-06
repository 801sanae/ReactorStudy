package com.kmy.study.backpressure;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.BufferOverflowStrategy;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
 * #5 Backpressure
 *   - Backpressure 전략 사용
 *      - BUFFER
 *           - BUFFER DROP LASTEST
 *           - BUFFER DROP OLDEST
 */
@Slf4j
public class BackpressureExample02 {
    public static void main(String[] args) throws InterruptedException {
//        #1 onBackpressureBuffer , BufferOverflowStrategy.DROP_LATEST
//        Flux
//                .interval(Duration.ofMillis(300L))
//                // 300밀리초마다 Long 값을 증가시키며 무한히 발행
//                .doOnNext(data -> log.info("# # doOnNext :: emitted by originial ::: {}", data))
//                // 원본 Flux에서 항목이 발행될 때마다 로그를 출력
//                .onBackpressureBuffer(2,
//                        dropped -> log.info("# overflow & dropped : {}", dropped),
//                        BufferOverflowStrategy.DROP_LATEST)
//                // 크기가 2인 버퍼를 사용하여 백프레셔 상황을 관리
//                // 버퍼가 가득 차면 새로운 항목을 버리고 로그를 출력
//                // DROP_LATEST 전략을 사용하여 새로운 항목을 버림
//                .doOnNext(data -> log.info("# # doOnNext :: emitted by Buffer : {}", data))
//                // 버퍼에서 항목이 발행될 때마다 로그를 출력
//                .publishOn(Schedulers.parallel(), false, 1)
//                // 병렬 스케줄러를 사용하여 다운스트림 작업을 실행
//                // prefetch 값을 1로 설정하여 한 번에 하나의 항목만 요청
//                .subscribe(data -> {
//                            try {
//                                Thread.sleep(1000L);
//                            } catch (InterruptedException e) {}
//                            log.info("# onNext : {}", data);
//                        },
//                        error -> log.error("# onError :: {}", error));
//                        // 구독자가 각 항목을 처리하는데 1초가 걸리도록 설정
//                        // 처리된 항목과 에러를 로그로 출력
//        Thread.sleep(3000L);
//        // 메인 스레드를 3초 동안 대기시켜 비동기 작업이 실행될 시간을 제공



//        #2 onBackpressureBuffer , BufferOverflowStrategy.DROP_OLDEST
        Flux.interval(Duration.ofMillis(300L))
                .doOnNext(data -> log.info("# emitted by original FLux : {}", data))
                .onBackpressureBuffer(2
                        , dropped -> log.info("** overflow & Dropped: {} **", dropped)
                        , BufferOverflowStrategy.DROP_OLDEST)
                .doOnNext(data -> log.info("# emitted by buffer : {}" ,data))
                .publishOn(Schedulers.parallel(), false, 1)
                .subscribe(data -> {
                            try {
                                Thread.sleep(1000L);
                            } catch (InterruptedException e) {}
                            log.info("# onNext : {}", data);
                        },
                        error -> log.error("# onError :: {}", error));
        Thread.sleep(2500L);
    }
}
