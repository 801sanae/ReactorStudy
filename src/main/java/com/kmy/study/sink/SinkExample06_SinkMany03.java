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

/*
 * 특정 시점으로 퇴돌린 데이터부터 emit한다.
 */
@Slf4j
public class SinkExample06_SinkMany03 {
    public static void main(String[] args) throws InterruptedException{
        /*
         * Sinks가 Publisher 역할시 Hot Publisher 동작
         */
        Sinks.Many<Integer> replaySink = Sinks.many().replay().limit(2); // -> 가장 나중에 emit된 데이터부터 subscirber에게 전달

//        Sinks.Many<Integer> replaySink = Sinks.many().replay().all(); // --> 이미 emit된 데이터여도 처음부터 모든 데이터가 subscirber에게 전달된다.

        // Help building Sinks.Many that will broadcast signals
        // to multiple Subscriber with the ability to retain and replay all or an arbitrary number of elements.
        // Sinks.MulticastReplaySpec
        /*
         * emit한 데이터를 다시 replay하여 구독전 이미 emit된 데이터도 subscriber가 전달 받을 수 있다.
         */

        Flux<Integer> fluxView = replaySink.asFlux();

        replaySink.emitNext(1, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(2, Sinks.EmitFailureHandler.FAIL_FAST);
        replaySink.emitNext(3, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscriber 1 : {}", data));

        replaySink.emitNext(4, Sinks.EmitFailureHandler.FAIL_FAST);

        fluxView.subscribe(data -> log.info("# subscriber 2 : {}", data));

        /*
17:52:30.935 [main] INFO com.kmy.study.sink.SinkExample06_SinkMany03 -- # subscriber 1 : 2
17:52:30.937 [main] INFO com.kmy.study.sink.SinkExample06_SinkMany03 -- # subscriber 1 : 3
17:52:30.937 [main] INFO com.kmy.study.sink.SinkExample06_SinkMany03 -- # subscriber 1 : 4
17:52:30.937 [main] INFO com.kmy.study.sink.SinkExample06_SinkMany03 -- # subscriber 2 : 3
17:52:30.937 [main] INFO com.kmy.study.sink.SinkExample06_SinkMany03 -- # subscriber 2 : 4
         */
    }
}
