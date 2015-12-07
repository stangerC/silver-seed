package com.silver.seed.query.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.silver.seed.core.entity.Entity;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by Liaojian on 2015/1/27.
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "QR_TABLE")
public class Table implements Entity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @javax.persistence.Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @javax.persistence.Column(name = "ALIAS", length = 10)
    private String alias;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "QUERY_ID")
    private com.silver.seed.query.entity.Query query;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public com.silver.seed.query.entity.Query getQuery() {
        return query;
    }

    public void setQuery(com.silver.seed.query.entity.Query query) {
        this.query = query;
    }

    @Override
    public String getId() {
        return id;
    }

    public String generateSql() {
        StringBuilder sb = new StringBuilder("");
        sb.append(getName());
        if(getAlias() != null) {
            sb.append(" as ").append(getAlias());
        }

        return sb.toString();
    }
}
