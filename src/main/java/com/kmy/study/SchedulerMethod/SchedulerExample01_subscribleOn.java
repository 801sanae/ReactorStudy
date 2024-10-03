package com.kmy.study.SchedulerMethod;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 *  subscribeOn 원본 Datasource에 대한 스케줄러 설정
 *  doOnSubscribe 추가적인 처리를 위해
 *  동시성을 가지는 논리적인 스레드
 */
@Slf4j
public class SchedulerExample01_subscribleOn {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7})
                // #1 구독이 발생한 직후 실행될 스레드를 지정하는 operator
                // #1-1 스케줄러 스레드 설정으로 원본 flux emit의 스레드가 메인스레드가 아니라 추가된 스레드로 실행됨.
                .subscribeOn(Schedulers.boundedElastic())
                // #2 원본 flux의 원본데이터 emit
                .doOnNext(data -> log.info("# doOnNext() : {}", data))
                // #3 구독이 발생한 시점 직후 추가 처리 동작처리
                .doOnSubscribe(subscription -> log.info("# doOnSubscrible"))
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(500L);
    }
/*
00:31:40.989 [main] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnSubscrible
00:31:40.993 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnNext() : 1
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # onNext : 1
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnNext() : 3
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # onNext : 9
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnNext() : 4
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # onNext : 16
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnNext() : 6
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # onNext : 36
00:31:40.994 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # doOnNext() : 7
00:31:40.995 [boundedElastic-1] INFO com.kmy.study.Scheduler.SchedulerExample01_subscribleOn -- # onNext : 49
 */
}
