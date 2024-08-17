package com.kmy.study.sink;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

/**
 * packageName    : com.kmy.study.sink
 * fileName       : SinkExample04_SinkMany01
 * author         : kmy
 * date           : 8/17/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 8/17/24        kmy       최초 생성
 */
@Slf4j
public class SinkExample05_SinkMany02 {
    public static void main(String[] args) throws InterruptedException{
        /*
         * Sinks가 Publisher 역할시 Hot Publisher 동작
         */
        Sinks.Many<Integer> mulitcastSink = Sinks.many().multicast().onBackpressureBuffer();

        //onBackpressureBuffer <- warm up,, hot sequence 동작

        // Help building Sinks.Many that will broadcast signals to multiple Subscriber
        // Sinks.MulticastSpec
        /*
         * UnicatProcessor <-- 얘는 단 하나의 subscriber에게 데이터를 emit 한다
         */

        Flux<Integer> fluxView = mulitcastSink.asFlux();

        mulitcastSink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        mulitcastSink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscriber 1 : {}", data));
        fluxView.subscribe(data -> log.info("# subscriber 2 : {}", data));

        mulitcastSink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);
        /*
17:47:55.289 [main] INFO com.kmy.study.sink.SinkExample05_SinkMany02 -- # subscriber 1 : 1
17:47:55.290 [main] INFO com.kmy.study.sink.SinkExample05_SinkMany02 -- # subscriber 1 : 2
17:47:55.291 [main] INFO com.kmy.study.sink.SinkExample05_SinkMany02 -- # subscriber 1 : 3
17:47:55.291 [main] INFO com.kmy.study.sink.SinkExample05_SinkMany02 -- # subscriber 2 : 3
         */
    }
}
