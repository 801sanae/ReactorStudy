package com.kmy.study.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

/**
 * packageName    : com.kmy.study.sink
 * fileName       : SinkExample03_SinkOne
 * author         : kmy
 * date           : 8/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/17/24        kmy       최초 생성
 */
/*
 * Mono 방식유사 한건의 데이터를 프로그래밍 방식으로 emit
 */
@Slf4j
public class SinkExample03_SinkOne {
    public static void main(String[] args) throws InterruptedException{
        Sinks.One<String> sinkOne = Sinks.one();

        Mono<String> mono = sinkOne.asMono();

        /* Sinks.EmitFailureHandler.FAIL_FAST
            A pre-made handler that will not instruct to retry any failure and trigger the failure handling immediately.
            에러 발생시 재시도하지않고 즉시 실패 처리
            1. --> 스레드 간 경합시 발생하는 데드락을 미연 방지 ->> Thread Safety 보장
            2. 처음 emit 한 데이터는 정상 emit 나머지 데이터는 Drop한다.
         */
        sinkOne.emitValue("Hello Reactor", Sinks.EmitFailureHandler.FAIL_FAST);
        sinkOne.emitValue("Hi Reactor", Sinks.EmitFailureHandler.FAIL_FAST);

        mono.subscribe(data -> log.info("# subscirber1", data));
        mono.subscribe(data -> log.info("# subscirber2", data));

    }
}
