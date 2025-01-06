package com.kmy.study.operators;

import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * groupBy 그룹화
 *
 * zipWith
 * 두개의 mono를 하나로 합친다.
 */
@Slf4j
public class OperEx30_groupBy02 {

    public static void main(String[] args) {
        Flux.fromIterable(SampleData.books)
                .groupBy(Book::getAuthorName)
                .flatMap(groupedFlux ->
                    Mono.just(groupedFlux.key())
                            .zipWith(groupedFlux.map(book -> (int)(book.getPrice() * book.getStockQuantity() * 0.1))
                                                .reduce((y1,y2) -> y1 + y2)
                                    , (authorName, sumRoyalty) -> authorName + "'s royalty: " + sumRoyalty)
                )
                .subscribe(log::info);
    }
}
/*
23:38:50.563 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- Kevin's royalty: 1280000
23:38:50.565 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- Mike's royalty: 2080000
23:38:50.565 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- Tom's royalty: 986000
23:38:50.565 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- Grace's royalty: 970000
23:38:50.565 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- Smith's royalty: 1500000
 */