package com.kmy.study.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/*
 *  parallel
 *  Round Robin 방식으로 CPU 코어 개수만큼 스레드를 병렬로 실행 -> 논리적인 코어(물리적인 스레드)의 개수를 의미
 *  병렬성을 가지는 물리적인 스레드
 *
 *  R.R 방식으로 CPU의 논리적인 코어 수에 맞게 데이터를 그룹화 -> rail
 */
@Slf4j
public class SchedulerExample03_parallel {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,4,6,7,11,12,15,16,19,21})
                .parallel(5)
                .runOn(Schedulers.parallel())
                .subscribe(data -> log.info("# onNext : {}", data*data));

        Thread.sleep(100L);
    }
/*
00:42:15.828 [parallel-5] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 49
00:42:15.828 [parallel-11] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 441
00:42:15.828 [parallel-3] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 16
00:42:15.828 [parallel-6] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 121
00:42:15.828 [parallel-2] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 9
00:42:15.828 [parallel-8] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 225
00:42:15.828 [parallel-1] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 1
00:42:15.828 [parallel-4] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 36
00:42:15.828 [parallel-9] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 256
00:42:15.828 [parallel-7] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 144
00:42:15.828 [parallel-10] INFO com.kmy.study.Scheduler.SchedulerExample03_parallel -- # onNext : 361
 */
}
