package com.bookroles.controller.view;

/**
 * @Author: dlus91
 * @Date: 2023/10/1 15:33
 */
public enum InputInterface {

    Login("/system/login"),
    Index("/system/queryIndex"),
    BookPage("/system/book/queryPage"),
    BookDetail("/system/book/detail");


    private final String name;

    InputInterface(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
