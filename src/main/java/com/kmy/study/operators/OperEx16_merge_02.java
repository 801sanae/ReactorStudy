package com.kmy.study.operators;


import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * merge
 * 모든 publisher가 subscrible된다.
 * publisher의 seq에서 emit된 데이터가 동시에 emit 된다. (인터리빙 방식)
 * emit된 시간 순서대로 merge한다.
 */
@Slf4j
public class OperEx16_merge_02 {

    public static void main(String[] args) throws InterruptedException{

        String[] usaStates = {"Ohio","Michigan","New Jersey","Illinois","New Hampshire","Virginia","Vermont","North Carolina","Ontario","Georgia","Ohio"};

        Flux
                .merge(getMeltDownRecoveryMessage(usaStates))
                .subscribe(log::info);


        Thread.sleep(2000L);
    }

    private static List<Mono<String>> getMeltDownRecoveryMessage(String[] usaStates){
        List<Mono<String>> message = new ArrayList<>();
        Arrays.stream(usaStates).forEach(usaState->{
            message.add(SampleData.nppMap.get(usaState));
        });

        return message;
    }
}
