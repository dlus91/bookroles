package com.bookroles.controller.valid;

import javax.validation.constraints.Null;

/**
 * @Author: dlus91
 * @Date: 2023/9/30 16:51
 */
public interface Group {

    /**
     * 只需要验证id
     */
    interface SelectOne{

    }

    /**
     * 查询所有与更新 验证规则一致
     */
    interface Select{

    }

    /**
     * 除了id都需验证
     */
    interface Add{

    }

    interface Update{

    }

    interface Update1{

    }

    interface Update2{

    }

    interface Delete{

    }

}
