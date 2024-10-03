package com.kmy.study.SchedulerMethod.Scheduler;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.SchedulerMethod.Scheduler
 * fileName       : Ex01_immediate
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
 *
 * 작업을 예약하는 대신 즉시 실행합니다.
 * 결과적으로 작업은 제출한 스레드(예: 운영자가 현재 onNextonCompleteonError 신호를 처리하고 있는 스레드)에서 실행됩니다.
 * --> "이 스케줄러는 일반적으로 스케줄러가 필요하지만 스레드를 변경하고 싶지 않은 API에 대한 "널 개체"로 사용됩니다."
 * 반환: 작업을 예약하는 대신 즉시 작업을 실행하는 재사용 가능한 스케줄러
 */
@Slf4j
public class Ex01_immediate {

    public static void main(String[] args) throws InterruptedException{
        Flux.fromArray(new Integer[]{1,3,5,7})
                .publishOn(Schedulers.parallel())
                .filter(data -> data > 3)
                .doOnNext(data -> log.info("# doOnNext filter : {}", data))
                // #1 Thread를 변경하지 않고 operator 실행시 사용?
                .publishOn(Schedulers.immediate())
                .map(data -> data * 10)
                .doOnNext(data -> log.info("# doOnNext map : {}", data))
                .subscribe(data->log.info("onNext : {}", data));

        Thread.sleep(200L);
    }
}
