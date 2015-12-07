package com.silver.seed.query.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silver.seed.core.entity.Entity;
import com.silver.seed.query.Result;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * @author Liaojian
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "QR_COLUMN")
public class Column implements Entity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    /**
     * 列的名字
     */
    @javax.persistence.Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @javax.persistence.Column(name = "ALIAS", length = 50, nullable = false)
    private String alias;

    /**
     * 列的标签，用于显示
     */
    @Transient
    private String label;

    @Transient
    private String value;

    @Transient
    private String index;

    @Transient
    private Integer width;

    @Transient
    private String align;

    @Transient
    private int type;

    @Transient
    private boolean sortable;
    /**
     * Column所属的DataSet
     */
    @Transient
    private Result dataSet;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUERY_ID")
    private com.silver.seed.query.entity.Query query;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "TABLE_ID")
    private Table table;

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Result getDataSet() {
        return dataSet;
    }

    public void setDataSet(Result dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public String getId() {
        return id;
    }

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String generateSql() {
        StringBuilder sb = new StringBuilder(getName());
        if(getAlias() != null) {
            sb.append(" as ").append(getAlias());
        }

        return sb.toString();
    }
}
