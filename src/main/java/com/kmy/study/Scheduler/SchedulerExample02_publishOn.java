package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class SchedulerExample02_publishOn {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                .doOnNext(data -> log.info("# doOnNext() : {}", data)) // #2 mainThread
                .doOnSubscribe(subscription -> log.info("# doOnSubscrible")) //#2 mainThread
                .publishOn(Schedulers.parallel()) // #1 DownStream의 실행 스레드를 변경한다. 별도의 스레드를 생성하여 수행.
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
00:38:42.500 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnSubscrible
00:38:42.507 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnNext() : 1
00:38:42.508 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnNext() : 3
00:38:42.509 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # onNext : 1
00:38:42.509 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnNext() : 4
00:38:42.509 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # onNext : 9
00:38:42.509 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnNext() : 6
00:38:42.509 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # onNext : 16
00:38:42.509 [main] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # doOnNext() : 7
00:38:42.509 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # onNext : 36
00:38:42.509 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample02_publishOn -- # onNext : 49
 */
}
