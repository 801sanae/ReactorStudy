package com.kmy.study.operators;

import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.IllegalFormatException;

/**
 * onErrorReturn
 * 대체값을 리턴한다.
 *
 * 특정 예외 타입을 지정해서 지정된 타입의 예외가 발생한 경우에만 대체 값을 emit하도록 한다.
 */
@Slf4j
public class OperEx22_onErrorReturn02 {

    public static void main(String[] args) {
        getBooks()
                .map(book -> book.getPenName().toUpperCase())
                .onErrorReturn(NullPointerException.class, "No Pen name")
                .onErrorReturn(IllegalFormatException.class, "Illegal Pen name")
                .subscribe(log::info);
    }

    private static Flux<Book> getBooks() {
        return Flux.fromIterable(SampleData.books);
    }
}
