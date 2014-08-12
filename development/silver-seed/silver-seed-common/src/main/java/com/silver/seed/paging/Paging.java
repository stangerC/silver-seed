package com.silver.seed.paging;

/**
 * 分页对象
 * @author Liaojian
 */
public class Paging {       
    
    private int pageNumber;
    
    private int pageSize;        
    
    private long rows;
    
    private int[] enablePageSize;
    
    public int DEFAULT_PAGE_SIZE = 20;        
    
    public Paging() {
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    
    public Paging(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return (int)(rows % pageSize == 0 ? rows / pageSize : rows / pageSize + 1);
    }    

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        if(pageNumber < 0) {
            this.pageNumber = 0;
        } else {
            this.pageNumber = pageNumber;
        }
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if(this.pageSize <= 0) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        else {
            this.pageSize = pageSize;
        }
    }        

    public int[] getEnablePageSize() {
        return enablePageSize;
    }

    public void setEnablePageSize(int[] enablePageSize) {
        this.enablePageSize = enablePageSize;
    }  

    public long getRows() {
        return rows;
    }

    public void setRows(long rows) {
        this.rows = rows;
    }        
}
