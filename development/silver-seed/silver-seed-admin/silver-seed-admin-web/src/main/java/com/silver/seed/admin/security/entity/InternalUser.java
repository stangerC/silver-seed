package com.silver.seed.admin.security.entity;

import com.silver.seed.core.entity.Entity;
import com.silver.seed.core.security.authc.entity.User;

/**
 * Created by Liaojian on 2015/12/17.
 */
public class InternalUser implements Entity<String>, User{

    private String id;

    private String userName;

    private String password;

    public InternalUser() {}

    public InternalUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
