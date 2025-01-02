package com.kmy.study.operators;

import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

/**
 * groupBy(keyMapper, valueMapper)
 *
 * GroupedFlux로 리턴, 그룹별 작업수행가능
 */
@Slf4j
public class OperEx30_groupBy01 {

    public static void main(String[] args) {
        ex01();
        ex02();
    }
    private static void ex01() {
        Flux.fromIterable(SampleData.books)
                .groupBy(book -> book.getAuthorName())
                .flatMap(stringBookGroupedFlux ->
                        stringBookGroupedFlux.map(book ->
                                book.getBookName() + "(" + book.getAuthorName() + ")").collectList()
                )
                .subscribe(bookByAuther -> log.info("book by author : {}", bookByAuther));
    }
    private static void ex02() {
        Flux.fromIterable(SampleData.books)
                .groupBy(book -> book.getAuthorName(), book -> book.getBookName() + "(" + book.getAuthorName() + ")")
                .flatMap(stringStringGroupedFlux -> stringStringGroupedFlux.collectList())
                .subscribe(bookByAuthor -> log.info("book by author : {}", bookByAuthor));
    }
}
//00:50:13.134 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- book by author : [Advance Kotlin(Kevin), Getting started Kotlin(Kevin)]
//00:50:13.142 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- book by author : [Advance Javascript(Mike), Getting started Javascript(Mike)]
//00:50:13.142 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- book by author : [Advance Java(Tom), Getting started Java(Tom)]
//00:50:13.142 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- book by author : [Advance Python(Grace), Getting started Python(Grace)]
//00:50:13.142 [main] INFO com.kmy.study.operators.OperEx30_groupBy02 -- book by author : [Advance Reactor(Smith), Getting started Reactor(Smith)]