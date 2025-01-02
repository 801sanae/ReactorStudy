package com.kmy.study.operators;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * buffer
 *
 * buffer(n) n 만큼 List 버퍼로 한번에 emit한다.
 */
@Slf4j
public class OperEx28_buffer {

    public static void main(String[] args) {
        Flux.range(1,95)
                .buffer(10)
                .subscribe(buffer -> log.info("#doOnNext : {}", buffer));
    }
}
/*
> Task :OperEx28_buffer.main()
00:31:35.077 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]
00:31:35.080 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [11, 12, 13, 14, 15, 16, 17, 18, 19, 20]
00:31:35.080 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [21, 22, 23, 24, 25, 26, 27, 28, 29, 30]
00:31:35.080 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [31, 32, 33, 34, 35, 36, 37, 38, 39, 40]
00:31:35.080 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [41, 42, 43, 44, 45, 46, 47, 48, 49, 50]
00:31:35.080 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [51, 52, 53, 54, 55, 56, 57, 58, 59, 60]
00:31:35.081 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [61, 62, 63, 64, 65, 66, 67, 68, 69, 70]
00:31:35.081 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [71, 72, 73, 74, 75, 76, 77, 78, 79, 80]
00:31:35.081 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [81, 82, 83, 84, 85, 86, 87, 88, 89, 90]
00:31:35.081 [main] INFO com.kmy.study.operators.OperEx28_buffer -- #doOnNext : [91, 92, 93, 94, 95]
 */