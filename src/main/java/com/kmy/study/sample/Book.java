package com.kmy.study.sample;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * packageName    : com.kmy.study.sample
 * fileName       : Book
 * author         : kmy
 * date           : 10/6/24
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 10/6/24        kmy       최초 생성
 */
@Getter
@AllArgsConstructor
public class Book {

    private String bookName;
    private String authorName;
    private String penName;
    private int price;
    private int stockQuantity;
}
