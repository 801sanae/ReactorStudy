package com.kmy.study.backpressure;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/*
 * #5 Backpressure
 *   - 데이터 개수 제어
 *   - Backpressure 전략 사용
 *      - IGNORE
 *          - Backpressure 미사용
 *      - ERROR
 *           - DownStream의 데이터 처리 속도가 느려서 UpStream의 emit 속도를 따라가지 못할때 에러 발생시키는 전략
 *      - DROP
 *           - Pub이 DownStream으로 전달할 데이터가 버퍼에 가득찰때 버퍼 밖에 대기 중 데이터 중에서 먼저 emit된 데이터 부터 drop하는 전략?
 *      - LASTEST
 *           - Pub이 DownStream으로 전달할 데이터가 버퍼에 가득찰때 버퍼 밖에 대기 중 데이터 중 가장 최근에 emit된 데이터 부터 buffer를 채우는 전략
 *      - BUFFER
 *           - BUFFER DROP LASTEST
 *           - BUFFER DROP OLDEST
 */
@Slf4j
public class BackpressureExample01 {
    public static void main(String[] args) throws InterruptedException {
//        #1 onBackpressureError
//        Flux
//                .interval(Duration.ofMillis(1L))
//                .onBackpressureError()
//                .doOnNext(data -> log.info("# doOnNext::: data:: {} ", data)) // publisher가 emit 한 데이터 확인 (디버그용)
//                .publishOn(Schedulers.parallel()) // 별도의 스레드 발생
//                .subscribe(data -> {
//                    try {
//                        Thread.sleep(5L);
//                    } catch (InterruptedException e) {
//                        throw new RuntimeException(e);
//                    }
////                    System.out.println("# onNext::: data::" + data);
//                    log.info("# onNext::: data::{}" , data);
//                });
//        Thread.sleep(5L);

//        #2 onBackpressureDrop
//
        Flux
                .interval(Duration.ofMillis(1L))
                .onBackpressureDrop(dropped -> log.info("dropped :: {}, dropped"))
                .publishOn(Schedulers.parallel()) // 별도의 스레드 발생
                .subscribe(data -> {
                    try {
                        Thread.sleep(5L);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
//                    System.out.println("# onNext::: data::" + data);
                    log.info("# onNext::: data::{}" , data);
                });
        Thread.sleep(5L);
    }
}
