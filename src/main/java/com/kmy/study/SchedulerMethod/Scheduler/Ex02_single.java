package com.kmy.study.SchedulerMethod.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.SchedulerMethod.Scheduler
 * fileName       : Ex02_single
 * author         : kmy
 * date           : 10/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/3/24        kmy       최초 생성
 */

/**
 * packageName : com.kmy.study.SchedulerMethod
 * 작업을 하나의 스레드에서 실행합니다.
 * 하나의 스레드를 재사용하면서, 다수의 작업을 처리
 * 하나의 스레드로 다수의 작업을 처리해야하여 지연시간이 짧은 작업을 처리하는것이 효과적
 */
@Slf4j
public class Ex02_single {

    public static void main(String[] args) throws InterruptedException{
        // #1 두번 호출하였지만 single Thread를 사용.
        doTask("task1")
                .subscribe(data -> log.info("onNext : {}", data));

        doTask("task2")
                .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[]{1,3,5,7})
                .publishOn(Schedulers.single())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter : {}", taskName, data))
                .map(data -> data* 10)
                .doOnNext(data -> log.info("# {} doOnNext map : {}", taskName, data));

    }
}

/*
16:52:00.343 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task1 doOnNext filter : 5
16:52:00.344 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task1 doOnNext map : 50
16:52:00.344 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- onNext : 50
16:52:00.344 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task1 doOnNext filter : 7
16:52:00.344 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task1 doOnNext map : 70
16:52:00.344 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- onNext : 70
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task2 doOnNext filter : 5
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task2 doOnNext map : 50
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- onNext : 50
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task2 doOnNext filter : 7
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- # task2 doOnNext map : 70
16:52:00.345 [single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex02_single -- onNext : 70
 */