package com.silver.seed.query.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silver.seed.core.entity.Entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Liaojian on 2014/10/7.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "QR_CONDITION")
public class Condition implements Entity<String>{

    public static String DATA_TYPE_STRING = "String";

    public static String DATA_TYPE_DECIMAL = "Decimal";

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @javax.persistence.Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @javax.persistence.Column(name = "COL", length = 50, nullable = false)
    private String column;

    @javax.persistence.Column(name = "DATA_TYPE", length = 50, nullable = false)
    private String dataType;

    @javax.persistence.Column(name = "OPERATION", length = 50, nullable = false)
    private String operation;

    @javax.persistence.Column(name = "VALUE", length = 50, nullable = false)
    private String value;

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

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String getId() {
        return id;
    }

    public Query getQuery() {
        return query;
    }

    public void setQuery(Query query) {
        this.query = query;
    }

    public String generateSql() {
        StringBuilder sb = new StringBuilder("");

        if(getTable().getAlias() != null) {
            sb.append(getTable().getName());
        }else {
            sb.append(getTable().getAlias());
        }

        sb.append(getOperation());

        if(DATA_TYPE_STRING.equals(getDataType())) {
            sb.append("'").append(getValue()).append("'");
        }
        else {
            sb.append(getValue());
        }

        return sb.toString();
    }
}
