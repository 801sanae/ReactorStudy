package com.kmy.study.SchedulerMethod.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.SchedulerMethod.Scheduler
 * fileName       : Ex03_newSingle
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
 * newSingle Scheduler를 사용합니다.
 */
@Slf4j
public class Ex03_newSingle {

    public static void main(String[] args) throws InterruptedException {
        doTask("task1")
                .subscribe(data -> log.info("onNext : {}", data));

        doTask("task2")
                .subscribe(data -> log.info("onNext : {}", data));

        Thread.sleep(200L);
    }

    private static Flux<Integer> doTask(String taskName) {
        return Flux.fromArray(new Integer[]{1,3,5,7})
                // #1 newSingle Scheduler를 publishOn()에 사용
                /**
                 * name : task-name
                 * daemon : main thread가 종료되면 종료 -> true
                 */
                .publishOn(Schedulers.newSingle("new-single", true))
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# {} doOnNext filter : {}", taskName, data))
                .map(data -> data* 10)
                .doOnNext(data -> log.info("# {} doOnNext map : {}", taskName, data));

    }
}

/*
16:57:19.604 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task2 doOnNext filter : 5
16:57:19.604 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task1 doOnNext filter : 5
16:57:19.611 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task1 doOnNext map : 50
16:57:19.611 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task2 doOnNext map : 50
16:57:19.612 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- onNext : 50
16:57:19.612 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- onNext : 50
16:57:19.612 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task1 doOnNext filter : 7
16:57:19.612 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task2 doOnNext filter : 7
16:57:19.612 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task1 doOnNext map : 70
16:57:19.612 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- # task2 doOnNext map : 70
16:57:19.612 [new-single-1] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- onNext : 70
16:57:19.612 [new-single-2] INFO com.kmy.study.SchedulerMethod.Scheduler.Ex03_newSingle -- onNext : 70
 */