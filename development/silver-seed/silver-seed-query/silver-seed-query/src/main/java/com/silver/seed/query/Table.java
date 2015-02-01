package com.silver.seed.query;

/**
 * Created by Liaojian on 2015/1/27.
 */
public class Table {
    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    private String alias;


}
