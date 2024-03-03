package com.bookroles.vo;

import java.security.Provider;

/**
 * @Author: dlus91
 * @Date: 2023/9/21 13:25
 */
public class LocalProvider extends Provider {

    public LocalProvider(String name, String versionStr, String info) {
        super(name, versionStr, info);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
