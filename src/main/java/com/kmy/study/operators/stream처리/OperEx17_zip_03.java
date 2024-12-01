package com.kmy.study.operators.stream처리;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuple3;

import java.time.Duration;

/**
 * zip
 * 각 publisher가 데이터를 하나씩 emit할 때까지 기다렸다가 결합
 */
@Slf4j
public class OperEx17_zip_03 {

    public static void main(String[] args) throws InterruptedException{

        getInfectedPersonsPerHour(10,21)
                .subscribe(t->{
                    Tuple3<Tuple2,Tuple2,Tuple2> t3 = (Tuple3) t;
                    int sum = (int) t3.getT1().getT2()
                                + (int) t3.getT2().getT2() + (int) t3.getT3().getT2();
                    log.info("{}, {}", t3.getT1().getT1(), sum);
                });

        Thread.sleep(2000L);
    }

    private static Flux getInfectedPersonsPerHour(int start, int end){
        return Flux.zip(
                Flux.fromIterable(SampleData.seoulInfected)
                        .filter(t2->t2.getT1() >= start && t2.getT1() <= end),
                Flux.fromIterable(SampleData.incheonInfected)
                        .filter(t2->t2.getT1() >= start && t2.getT1()<=end),
                Flux.fromIterable(SampleData.suwonInfected)
                        .filter(t2->t2.getT1() >= start && t2.getT1()<=end)
        );
    }
}
