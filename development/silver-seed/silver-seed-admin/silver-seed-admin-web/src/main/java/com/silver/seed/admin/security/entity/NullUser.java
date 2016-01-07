package com.silver.seed.admin.security.entity;

import com.silver.seed.core.security.authc.entity.User;

/**
 * Created by Liaojian on 2015/12/16.
 */
public class NullUser implements User{
    private static NullUser nullUser = new NullUser();

    private NullUser() {}

    public static NullUser getInstance() {
        return nullUser;
    }
}
