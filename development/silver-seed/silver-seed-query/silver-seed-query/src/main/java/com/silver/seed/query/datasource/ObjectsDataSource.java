package com.silver.seed.query.datasource;

/**
 * Created by Liaojian on 2014/9/1.
 */
public class ObjectsDataSource implements DataSource {

    private Iterable objects;

    public ObjectsDataSource(Iterable objects) {
        this.objects = objects;
    }

    @Override
    public Iterable getData() {
        return objects;
    }
}
