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
 */
/*
    - onBackpressureError
        백프레셔 상황이 발생하면 오류를 발생시킵니다.
        OverflowException을 통해 스트림을 종료합니다.
    - onBackpressureDrop
        소비자가 처리할 수 없는 초과 항목들을 단순히 버립니다.
        선택적으로 버려진 항목에 대한 콜백을 제공할 수 있습니다.
    - onBackpressureLatest
        가장 최근의 항목만 유지하고 나머지는 버립니다.
        소비자가 처리할 준비가 되었을 때 최신 값만 전달합니다.
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
////                        throw new RuntimeException(e);
//                    }
////                    System.out.println("# onNext::: data::" + data);
//                    log.info("# onNext::: data::{}" , data);
//                });
//        Thread.sleep(5L);

//        #2 onBackpressureDrop
//        Flux
//                .interval(Duration.ofMillis(1L))
//                .onBackpressureDrop(dropped -> log.info("dropped :: {}", dropped)) // backpressure에 dropped 데이터를 파라미터로 전달받을 수 있다.
//                                                                                   // drop 된 데이터가 폐기되기전 처리를 할 수 있다.
//                                                                                   // 버퍼가 가득 찬 상태에서 버퍼가 비워질때까지 데이터를 drop한다.
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
//        Thread.sleep(2000L);

//        #3 onBackpressureLatest
        Flux.interval(Duration.ofMillis(1L))
                .onBackpressureLatest() // 버퍼가 가득 찼다가, 버퍼가 다시 비워지는 시간 동안
                                        // Emit되는 데이터가 가장 최근에 emit한 데이터가 된 후,
                                        // 다음 데이터가 emit하면 다시 폐기
                                        // ^- 반복
                .publishOn(Schedulers.parallel())
                .subscribe(data -> {
                    try{
                        Thread.sleep(5L);
                    }catch (InterruptedException e){}
                    log.info("# onNext :: {}", data);
                },
                        error->log.error("# onError :: {}", error)
                );
        Thread.sleep(2000L);

    }
}
