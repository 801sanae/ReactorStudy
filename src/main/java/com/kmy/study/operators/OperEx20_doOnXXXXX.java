package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Subscription;
import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;

/**
 * doOnXXXX
 *
 * 디버그용도
 * 데이터가 emit되는 과정에서 error발생하면 해당 에러에 대한 알림 전송하는 로직 추가등
 * 부수 효과를 위한 다양한 로직을 적용가능.
 *
 * doFirst : publisher가 구독되기 전에 트리거되는 동작을 추가 할 수 있다.
 * doOnSubsrible : publisher가 구독 중일때 트리거되는 동작을 추가 할 수 있다.
 * doOnRequest : publisher가 요청을 수신할 때 트리거되는 동작을 추가 할 수 있다.
 * doOnNext : publisher가 데이터를 emit할 때 트리거되는 동작을 추가 할 수 있다.
 * doOnEach : publisher가 데이터를 emti할 때 성공적으로 완료되엇을때 , 에러가 발생한 상태로 종료되었을때 트리거 되는 동작을 추가할 수 있다.
 * doOnComplete : publisher가 성공적으로 완료되었을때 트리거되는 동작을 추가 할 수 있다.
 * doOnTerminate : publisher가 성공적으로 완료되었을때 또는 에러가 발생한 상태로 종료되었을 때 트리거 되는 동작을 추가할 수 있다.
 * doAfterTerminate : Downstream을 성고적으로 완료한 직후  또는 에러가 발생하여 publisher가 종료된 직후에 트리거되는 동작을 추가할 수 있다.
 * doFinally : 에러를 포함해서 어떤 이유이든 간에 publisher가 종료된 후 트리거되는 동작을 추가 할 수 있다.
 *
 * doOnDiscard : Upstream에 있는 전체 operator 체인의 동작 중에서 operator에 의해 폐기되는 요소를 조건부로 정리 할 수 있다.
 * doOnError : publisher가 에러가 발생한 상태로 종료되었을 때 트리거되는 동작을 추가 할 수 있다.
 * doOnCancel : publisher가 취소되었을때 트리거되는 동작을 추가할 수 있다.
 */

/**
 * **부수효과**
 * * 함수형프로그래밍에서 정해진 결과를 돌려주는 것 이외의 어떤 일을 하게 되면 부수효과가 있는 함수라고함.
 */
@Slf4j
public class OperEx20_doOnXXXXX {

    public static void main(String[] args) {
        Flux.range(1,5)
                //doFianlly은 중복 호출일 경우 역순으로 호출
                .doFinally(signalType -> log.info("# doFinally 1: {}", signalType))
                .doFinally(signalType -> log.info("# doFinally 2: {}", signalType))
                .doOnNext(data -> log.info("# range > doOnNext : {}", data))
                //publisher가 요청을 수신할때 트리거되는 동작추가 가능
                .doOnRequest(data -> log.info("# doOnRequest : {}", data))
                //구독중일때 트리거되는 동작추가 가능
                .doOnSubscribe(subscription -> log.info("# doOnSubscribe 1"))
                //구독전에 트리거되는 동작추가 가능
                //위치와 상관없이 제일먼저 동작
                .doFirst(() -> log.info("# doFirst"))
                .filter(num -> num%2==1)
                //emit할때 트리거되는 동작추가 가능
                .doOnNext(data-> log.info("# filter > doOnNext :{}", data))
                .doOnEach(data-> log.info("# doOnEach : {}", data))
                //성공적으로 완료되었을때 트리거되는 동작추가 가능
                .doOnComplete(() -> log.info("# doOnComplete()"))
                //publisher가 성공, 에러가 발생한 상태로 종료가 되었을때 실행
                .doOnTerminate(()->log.info("# doOnTerminate"))
                //downstream을 성공, 에러가 발생하는 상태로 종료가 되었을때 실행
                .doAfterTerminate(()->log.info("# doAfterTerminate"))
                .subscribe(new BaseSubscriber<Integer>() {
                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(Integer value) {
                        log.info("# hookOnNext: {}", value);
                        request(1);
                    }
                });
    }
}
/*
> Task :OperEx20_doOnXXXXX.main()
11:58:19.884 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doFirst
11:58:19.891 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnSubscribe 1
11:58:19.893 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnRequest : 9223372036854775807
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # range > doOnNext : 1
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # filter > doOnNext :1
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnEach : doOnEach_onNext(1)
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # hookOnNext: 1
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnRequest : 1
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # range > doOnNext : 2
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # range > doOnNext : 3
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # filter > doOnNext :3
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnEach : doOnEach_onNext(3)
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # hookOnNext: 3
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnRequest : 1
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # range > doOnNext : 4
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # range > doOnNext : 5
11:58:19.896 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # filter > doOnNext :5
11:58:19.897 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnEach : doOnEach_onNext(5)
11:58:19.897 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # hookOnNext: 5
11:58:19.897 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnRequest : 1
11:58:19.897 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnEach : onComplete()
11:58:19.898 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnComplete()
11:58:19.898 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doOnTerminate
11:58:19.898 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doAfterTerminate
11:58:19.898 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doFinally 2: onComplete
11:58:19.898 [main] INFO com.kmy.study.operators.OperEx20_doOnXXXXX -- # doFinally 1: onComplete
 */
