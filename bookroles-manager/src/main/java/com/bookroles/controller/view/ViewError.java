package com.bookroles.controller.view;

/**
 * @Author: dlus91
 * @Date: 2023/10/1 15:19
 */
public enum ViewError {

    PARAMS("params_error");

    private final String name;

    ViewError(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
