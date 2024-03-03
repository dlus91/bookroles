package com.bookroles.controller.view;

/**
 * @Author: dlus91
 * @Date: 2023/10/1 15:33
 */
public enum OutputView {

    Login("/loginPage"),
    Index("/indexPage"),
    BookPage("/book/panelBookPage"),
    BookDetail("/book/panelBookPageProfile");


    private final String name;

    OutputView(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
