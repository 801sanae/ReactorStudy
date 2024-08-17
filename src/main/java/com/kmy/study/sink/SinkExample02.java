package com.kmy.study.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/**
 * packageName    : com.kmy.study.sink
 * fileName       : SinkExample02
 * author         : kmy
 * date           : 8/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/17/24        kmy       최초 생성
 */


/*
 * Sink로 signal을 emit 할 경우 스레드 안정성을 보장받을 수 있다.
 * 총 7개 스레드 발생
 */
@Slf4j
public class SinkExample02 {
    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;

        Sinks.Many<String> unicaskSink = Sinks.many().unicast().onBackpressureBuffer();
        Flux<String> fluxView = unicaskSink.asFlux();
        IntStream.range(1, tasks)
                .forEach(n->{
                    try {
                        new Thread(() -> {
                            unicaskSink.emitNext(doTask(n), Sinks.EmitFailureHandler.FAIL_FAST);
                            log.info("# emitted: {}", n);
                        }).start();// 별도 스레드 생성하여 실행
                        Thread.sleep(100L);
                    }catch (InterruptedException e){
                        log.error(e.getMessage());
                    }
                });

        fluxView.publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> log.info("# map() :: {}", n)) // 별도의 스레드로 map수행
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext: {}", data)); // 별도스레드 수행

        Thread.sleep(200L);
    }
    private static String doTask(int taskNum){
        return "task [" + taskNum + "] result";
    }
}
