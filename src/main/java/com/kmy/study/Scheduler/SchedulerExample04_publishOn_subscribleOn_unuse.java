package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/*
 * publishOn, subscribleOn 안쓰면
 * main Thread에서 operator 체인의 각 단계별로 실행.
 */
@Slf4j
public class SchedulerExample04_publishOn_subscribleOn_unuse {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                .doOnNext(data -> log.info("# doOnNext() fromArray: {}", data))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext() filter: {}", data))
                .map(data -> data*3)
                .doOnNext(data -> log.info("# doOnNext() map: {}", data))
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
00:56:08.958 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() fromArray: 1
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() fromArray: 3
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() fromArray: 4
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() filter: 4
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() map: 12
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # onNext : 144
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() fromArray: 6
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() filter: 6
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() map: 18
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # onNext : 324
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() fromArray: 7
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() filter: 7
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # doOnNext() map: 21
00:56:08.960 [main] INFO com.kmy.study.Scheduler.SchedulerExample04_publishOn_subscribleOn -- # onNext : 441
 */
}
