package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 * publishOn, subscribleOn 안쓰면
 * main Thread에서 operator 체인의 각 단계별로 실행.
 */
@Slf4j
public class SchedulerExample05_publishOn_use {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                .doOnNext(data -> log.info("# doOnNext() fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .doOnNext(data -> log.info("# doOnNext() filter: {}", data))
                .map(data -> data*3)
                .doOnNext(data -> log.info("# doOnNext() map: {}", data))
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
01:01:15.092 [main] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() fromArray: 1
01:01:15.095 [main] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() fromArray: 3
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() filter: 1
01:01:15.095 [main] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() fromArray: 4
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() map: 3
01:01:15.095 [main] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() fromArray: 6
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # onNext : 9
01:01:15.095 [main] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() fromArray: 7
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() filter: 3
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() map: 9
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # onNext : 81
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() filter: 4
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() map: 12
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # onNext : 144
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() filter: 6
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() map: 18
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # onNext : 324
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() filter: 7
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # doOnNext() map: 21
01:01:15.095 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample05_publishOn_use -- # onNext : 441
 */
}
