package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 * publishOn, subscribleOn 안쓰면
 * main Thread에서 operator 체인의 각 단계별로 실행.
 */
@Slf4j
public class SchedulerExample07_publishOn_subscribleOn_use {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                .subscribeOn(Schedulers.boundedElastic())
                .doOnNext(data -> log.info("# doOnNext() fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext() filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data*3)
                .doOnNext(data -> log.info("# doOnNext() map: {}", data))
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
01:02:56.701 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() fromArray: 1
01:02:56.703 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() fromArray: 3
01:02:56.703 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() fromArray: 4
01:02:56.703 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() filter: 4
01:02:56.704 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() fromArray: 6
01:02:56.704 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() filter: 6
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() map: 12
01:02:56.704 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() fromArray: 7
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # onNext : 144
01:02:56.704 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() filter: 7
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() map: 18
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # onNext : 324
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # doOnNext() map: 21
01:02:56.704 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample07_publishOn_subscribleOn_use -- # onNext : 441
 */
}
