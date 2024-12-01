package com.kmy.study.operators.upstreamRange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

/**
 * packageName    : com.kmy.study.operators
 * fileName       : OperEx01_justOrEmpty
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */

/**
 * Mono.justOrEmpty()
 * null, Optional.ofNullable(null) 같은 결과
 */
@Slf4j
public class OperEx01_justOrEmpty {

    public static void main(String[] args) {

        Mono
                // #1 Optional로 감싸서 보내도 null일 경우와 같은 결과
                .justOrEmpty(null)
                .subscribe(data -> {},
                           error -> {},
                           () -> log.info("onComplete")
                );

    }
}
