package com.kmy.study.operators;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Map;

/**
 * Flux.generate()
 * 프로그램 방식으로 signal 이벤트를 발생시킴.
 * 동기적
 * 데이터 순차적 emitt에 사용됨.
 * state는 내부적으로 1씩 증가하는 숫자 포함.
 * 숫자값 세팅으로 onComplete Signal을 발생
 * 마지막 인자 type(Consumer) 후처리 로그성 사용.
 */
@Slf4j
public class OperEx07_generate {

    public static void main(String[] args) {
        // #1
        Flux
                .generate(
                          () -> 0           //초기값
                        , (state, sink) ->{
                            sink.next(state);
                            if(state == 10) sink.complete();
                            return ++state;
                        })
                .subscribe(data -> log.info("# onNext: {}", data));
//        00:25:25.659 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 0
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 1
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 2
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 3
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 4
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 5
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 6
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 7
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 8
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 9
//        00:25:25.662 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext: 10

        // #2
        final int dan =3;
        Flux
                .generate(
                        ()-> Tuples.of(dan,1)
                        , (state, sink)-> {
                            sink.next(state.getT1() + " * " + state.getT2() + " = " + state.getT1() * state.getT2());
                            if(9 == state.getT2()) sink.complete();
                            return Tuples.of(state.getT1(), state.getT2() + 1);
                        }
                        , state->log.info("#구구단 {}단 종료!!", state.getT1()))
                .subscribe(data->log.info("# onNext : {}", data));
//        00:40:09.906 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 1 = 3
//        00:40:09.906 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 2 = 6
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 3 = 9
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 4 = 12
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 5 = 15
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 6 = 18
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 7 = 21
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 8 = 24
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- # onNext : 3 * 9 = 27
//        00:40:09.907 [main] INFO com.kmy.study.operators.생성.OperEx07_generate -- #구구단 3단 종료!!

        // #3
        Map<Integer, Tuple2<Integer, Long>> map = SampleData.getBtcTopPricesPerYearMap();

        Flux
                .generate(()->2019, (state, sink)->{
                    if(state > 2021) sink.complete();
                    else sink.next(map.get(state));
                    return ++state;
                })
                .subscribe(data->log.info("# onNext : {}", data));
    }
}
