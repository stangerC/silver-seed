package com.silver.seed.query;

import com.silver.seed.query.QueryResult;

/**
 * @author Liaojian
 */
public class Field {

    /**
     * 列的名字
     */
    private String name;

    /**
     * 列的标签，用于显示
     */
    private String label;

    private String value;
    private String index;
    private Integer width;
    private String align;
    private String type;
    private boolean sortable;
    /**
     * Column所属的DataSet
     */
    private QueryResult dataSet;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public String getAlign() {
        return align;
    }

    public void setAlign(String align) {
        this.align = align;
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public QueryResult getDataSet() {
        return dataSet;
    }

    public void setDataSet(QueryResult dataSet) {
        this.dataSet = dataSet;
    }
}
