package com.kmy.study.context;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * packageName    : com.kmy.study.context
 * fileName       : ContextEx01
 * author         : kmy
 * date           : 10/3/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/3/24        kmy       최초 생성
 */

/**
 * Context
 * 서로 다른 스레드에서도 context에 저장된 데이터에 접근하기 쉽다.
 */
@Slf4j
public class ContextEx01 {
    public static void main(String[] args) throws InterruptedException{
        // #1 원본 데이터 소스레벨에서 읽는 방식
        // #1-1 defer() operator와 같은 원리로 동작
        Mono.deferContextual(contextView ->
                Mono.just("Hello, " + contextView.get("firstName"))
                        .doOnNext(data -> log.info("doOnNext : {}", data))
                )
                .subscribeOn(Schedulers.boundedElastic())
                .publishOn(Schedulers.parallel())
                // #2 operator 체인 중간에서 읽는 방식
                .transformDeferredContextual((mono,contextView) ->
                        mono.map(data->  data+" " + contextView.get("lastName"))
                )
                // #3 context에 데이터 쓰기
                .contextWrite(context -> context.put("lastName", "Jobs"))
                .contextWrite(context -> context.put("firstName", "Steven"))
                .subscribe(data -> log.info("# onNext : {}", data));

        Thread.sleep(100L);
    }
}
