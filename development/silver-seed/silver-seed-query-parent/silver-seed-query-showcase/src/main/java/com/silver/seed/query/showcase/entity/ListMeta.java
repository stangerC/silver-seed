
package com.silver.seed.query.showcase.entity;

import com.silver.seed.paging.Paging;
import java.util.List;

/**
 *
 * @author Liaojian
 */
public class ListMeta {
    private List<String> columnNames;
    
    private Paging paging;

    public List<String> getColumnNames() {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames) {
        this.columnNames = columnNames;
    }

    public Paging getPaging() {
        return paging;
    }

    public void setPaging(Paging paging) {
        this.paging = paging;
    }        
}
