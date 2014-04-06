package com.silver.seed.query.entity;

import com.silver.seed.query.view.ViewMeta;

/**
 *
 * @author Liaojian
 */
public class TableMeta<T extends ViewMeta> {
    private ViewType viewType;
    private String title;
    private T viewMeta;
    
    public TableMeta(String title, ViewType viewType) {
        this.viewType = viewType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }    

    public T getViewMeta() {
        return viewMeta;
    }

    public void setViewMeta(T viewMeta) {
        this.viewMeta = viewMeta;
    }    
}
