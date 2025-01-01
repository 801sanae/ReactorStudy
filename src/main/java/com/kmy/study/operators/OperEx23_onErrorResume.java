package com.kmy.study.operators;

import com.kmy.study.sample.Book;
import com.kmy.study.sample.SampleData;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.List;


/**
 * onErrorResume
 * 대체동작을 수행하도록한다.
 * 대체동작에서도 오류가 발생하면 onError Signal을 전송한다.
 */
@Slf4j
public class OperEx23_onErrorResume {

    public static void main(String[] args) {
        final String keyword = "DDD";
        getBooksFromCache(keyword)
                .onErrorResume(error -> getBooksFromDatabase(keyword))
                .subscribe(data -> log.info("# onNext ::{}", data.getBookName())
                , error -> log.error("onError {}"));

    }

    public static Flux<Book> getBooksFromCache(final String keyword){
        return Flux.fromIterable(SampleData.books)
                .filter(book -> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("no such book")));
    }

    public static Flux<Book> getBooksFromDatabase(final String keyword){
        List<Book> books = new ArrayList<>(SampleData.books);
        books.add(new Book("DDD: domain driven design", "joy", "ddd-main", 35000, 200));
        return Flux.fromIterable(books)
                .filter(book-> book.getBookName().contains(keyword))
                .switchIfEmpty(Flux.error(new NoSuchBookException("no such book")));
    }

    private static class NoSuchBookException extends RuntimeException{
        NoSuchBookException(String msg){
            super(msg);
        }
    }
}
