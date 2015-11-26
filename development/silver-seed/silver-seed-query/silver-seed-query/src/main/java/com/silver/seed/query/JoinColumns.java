package com.silver.seed.query;

import com.silver.seed.core.entity.Entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Liaojian on 2015/1/27.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "QR_JOIN_COLUMNS")
public class JoinColumns implements Entity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @javax.persistence.Column(name = "TABLE_ONE", length = 50, nullable = false)
    private String tableOne;

    @javax.persistence.Column(name = "TABLE_TWO", length = 50, nullable = false)
    private String tableTwo;

    @javax.persistence.Column(name = "TABLE_ONE_ALIAS", length = 50)
    private String tableOneAlias;

    @javax.persistence.Column(name = "TABLE_TWO_ALIAS", length = 50)
    private String tableTwoAlias;

    @javax.persistence.Column(name = "COLUMN_ONE", length = 50, nullable = false)
    private String columnOne;

    @javax.persistence.Column(name = "COLUMN_TWO", length = 50, nullable = false)
    private String columnTwo;

    @ManyToOne
    @JoinColumn(name = "QUERY_ID")
    private Query query;

    public String getColumnTwo() {
        return columnTwo;
    }

    public void setColumnTwo(String columnTwo) {
        this.columnTwo = columnTwo;
    }

    public String getColumnOne() {
        return columnOne;
    }

    public void setColumnOne(String columnOne) {
        this.columnOne = columnOne;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTableOne() {
        return tableOne;
    }

    public void setTableOne(String tableOne) {
        this.tableOne = tableOne;
    }

    public String getTableTwo() {
        return tableTwo;
    }

    public void setTableTwo(String tableTwo) {
        this.tableTwo = tableTwo;
    }

    public String getTableOneAlias() {
        return tableOneAlias;
    }

    public void setTableOneAlias(String tableOneAlias) {
        this.tableOneAlias = tableOneAlias;
    }

    public String getTableTwoAlias() {
        return tableTwoAlias;
    }

    public void setTableTwoAlias(String tableTwoAlias) {
        this.tableTwoAlias = tableTwoAlias;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    @Override
    public String getId() {
        return id;
    }
}
