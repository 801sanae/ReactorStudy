package com.kmy.study.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.stream.IntStream;

/*
 * Sink는 명시적 Signal을 전송한다. ( doOnNext ,, )
 * Flux, Mono //
 * generate()
 * create()
 * 멀티 스레드 방식으로 signal을 보내며, 스레드 안정성을 보장한다.
 */
@Slf4j
public class SinkExample01 {

    public static void main(String[] args) throws InterruptedException {
        int tasks = 6;
        Flux
                .create((FluxSink<String> sink)->{
                    IntStream.range(1,tasks)
                            .forEach(n -> sink.next(doTasks(n)));
                })
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(n -> log.info("# create() : {}", n))
                .publishOn(Schedulers.parallel())
                .map(result -> result + " success!")
                .doOnNext(n -> log.info("# map() : {}", n))
                .publishOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext() : {}", data));
        Thread.sleep(500L);

    }

    public static String doTasks(int taskNum){
        return "Task " + taskNum + " result";
    }
}
