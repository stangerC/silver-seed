package com.silver.seed.query;

import com.silver.seed.core.paging.Paging;
import java.util.Collection;

/**
 *
 * @author Liaojian
 */
public class Result {

    /**
     * 数据集名称
     */
    private String name;

    private Paging paging;
    private Collection data;

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }

    public Collection getData() {
        return data;
    }

    public void setData(Collection data) {
        this.data = data;
    }        
}
