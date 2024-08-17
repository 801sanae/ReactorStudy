package com.kmy.study.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * packageName    : com.kmy.study.sink
 * fileName       : SinkExample04_SinkMany
 * author         : kmy
 * date           : 8/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/17/24        kmy       최초 생성
 */
@Slf4j
public class SinkExample04_SinkMany {
    public static void main(String[] args) throws InterruptedException{
        Sinks.Many<Integer> unicastSink = Sinks.many().unicast().onBackpressureBuffer();

        // Help building Sinks.Many that will broadcast signals to a single Subscriber
        // UnicastSpec
        /*
         * UnicatProcessor <-- 얘는 단 하나의 subscriber에게 데이터를 emit 한다
         */

        Flux<Integer> fluxView = unicastSink.asFlux();

        unicastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        unicastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscriber 1 : {}", data));

        unicastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

//        Caused by: java.lang.IllegalStateException: Sinks.many().unicast() sinks only allow a single Subscriber
//        단인 Subscirber만!
//        fluxView.subscribe(data -> log.info("# subscriber 2 : {}", data));

    }
}
