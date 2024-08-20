package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 * publishOn 두번쨰 사용될떄마다 새로운 Thread 생성하여 사용.
 * main Thread에서 operator 체인의 각 단계별로 실행.
 */
@Slf4j
public class SchedulerExample06_publishOn_repeat_use {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                .doOnNext(data -> log.info("# doOnNext() fromArray: {}", data))
                .publishOn(Schedulers.parallel())
                .doOnNext(data -> log.info("# doOnNext() filter: {}", data))
                .publishOn(Schedulers.parallel())
                .map(data -> data*3)
                .doOnNext(data -> log.info("# doOnNext() map: {}", data))
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
01:00:18.759 [main] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() fromArray: 1
01:00:18.761 [main] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() fromArray: 3
01:00:18.761 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() filter: 1
01:00:18.761 [main] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() fromArray: 4
01:00:18.762 [main] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() fromArray: 6
01:00:18.762 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() filter: 3
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() map: 3
01:00:18.762 [main] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() fromArray: 7
01:00:18.762 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() filter: 4
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # onNext : 9
01:00:18.762 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() filter: 6
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() map: 9
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # onNext : 81
01:00:18.762 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() filter: 7
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() map: 12
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # onNext : 144
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() map: 18
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # onNext : 324
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # doOnNext() map: 21
01:00:18.762 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample06_publishOn_repeat_use -- # onNext : 441
 */
}
