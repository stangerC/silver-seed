package com.silver.seed.paging;

/**
 * 分页对象
 * @author Liaojian
 */
public class Paging {   
    
    private int pageCount;
    
    private int pageNumber;
    
    private int pageSize;        
    
    private int[] enablePageSize;
    
    public int DEFAULT_PAGE_SIZE = 20;        
    
    public Paging() {
        this.pageSize = DEFAULT_PAGE_SIZE;
    }
    
    public Paging(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        if(pageCount < 0) {
            this.pageCount = 0;
        }
        else {
            this.pageCount = pageCount;
        }
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
}
