package com.silver.seed.query.entity.jqgrid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;
import com.silver.seed.core.paging.Paging;
import java.util.Collection;
import org.springframework.data.domain.Page;

/**
 *
 * @author Liaojian
 */
public class JqGridData<T> {
    public static final String INCLUDES = "includes";
    public static final String EXCLUDES = "excludes";
    
    private Collection<T> data;
    private Paging paging;
    private SimplePropertyPreFilter filter;

    public JqGridData(Collection<T> data, String[] names, String filterType, int pageSize, int page) {
        this.data = data;
        buildFilter(names, filterType);        
        buildPaging(data.size(), page, pageSize);
    }
    
    public JqGridData(Collection<T> data, String[] names, String filterType, long rows, int pageSize, int page) {
        this.data = data;
        buildFilter(names, filterType);        
        buildPaging(rows, page, pageSize);
    }
    
    public JqGridData(Page<T> page, String[] names, String filterType) {
        data = page.getContent();
        buildFilter(names, filterType);
        buildPaging(page.getTotalElements(), page.getNumber(), page.getSize());
    }

    public String toJsonString() {
        StringBuilder buffer = new StringBuilder("{\"data\":[");

        for (T element : data) {
            buffer.append(JSON.toJSONString(element, filter)).append(",");
        }

        buffer.delete(buffer.length() - 1, buffer.length());
        buffer.append("]");
        
        buffer.append(",\"total\":\"").append(paging.getPageCount()).append("\"");
        buffer.append(",\"records\":\"").append(paging.getRows()).append("\"");
        
        buffer.append("}");

        return buffer.toString();
    }

    private void buildPaging(long rows, int page, int pageSize) {
        paging = new Paging(pageSize);        
        paging.setPageNumber(page);
        paging.setRows(rows);
    }

    private void buildFilter(String[] names, String filterType) {
        filter = new SimplePropertyPreFilter(); 
        if(INCLUDES.equals(filterType)) {             
            for(String name : names) {
                filter.getIncludes().add(name);                
            }
        }
        else if(EXCLUDES.equals(filterType)){
            for(String name : names) {
                filter.getExcludes().add(name);                
            }
        }    
    }
    
}
