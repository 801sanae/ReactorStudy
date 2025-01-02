package com.kmy.study.operators;


import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * bufferTimeout(m,n) m : maxSize , n : maxTime
 *
 * m,n 조건에 해당될떄까지 List 버퍼로 emit한다.
 */
@Slf4j
public class OperEx29_bufferTimeout {

    public static void main(String[] args) {
        Flux.range(1,20)
                .map(num -> {
                    try{
                        if(num < 10) Thread.sleep(100L); // 1~9는 maxSize 3개씩 리스트로 넘기는데
                        else Thread.sleep(300L); //10부터는 3초걸리니 2개씩 리스트로 emit
                    }catch (InterruptedException e){}

                    return num;
                })
                .bufferTimeout(3, Duration.ofMillis(400L)) // maxSize : 3, maxTime : 1.5초
                .subscribe(buffer -> log.info("#doOnNext : {}", buffer));
    }
}
/*
> Task :OperEx29_bufferTimeout.main()
00:37:40.895 [main] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [1, 2, 3]
00:37:41.203 [main] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [4, 5, 6]
00:37:41.509 [main] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [7, 8, 9]
00:37:42.215 [parallel-1] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [10, 11]
00:37:42.816 [parallel-1] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [12, 13]
00:37:43.423 [parallel-1] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [14, 15]
00:37:44.032 [parallel-1] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [16, 17]
00:37:44.639 [parallel-1] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [18, 19]
00:37:44.841 [main] INFO com.kmy.study.operators.OperEx29_bufferTimeout -- #doOnNext : [20]
 */