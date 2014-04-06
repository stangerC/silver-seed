package com.silver.seed.query.entity;

import com.silver.seed.paging.Paging;
import java.util.Collection;

/**
 *
 * @author Liaojian
 */
public class DataTable<T> {
    private Paging paging;
    private Collection<T> data;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Collection<T> getData() {
        return data;
    }

    public void setData(Collection<T> data) {
        this.data = data;
    }        
}
