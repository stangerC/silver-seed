package com.silver.seed.query;

import com.silver.seed.core.entity.AuditableEntity;
import com.silver.seed.core.entity.Entity;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.*;

/**
 *
 * @author Liaojian
 */
@javax.persistence.Entity
@javax.persistence.Table(name = "QR_QUERY")
public class Query implements Entity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "uuid")
    @GenericGenerator(name="uuid", strategy = "org.hibernate.id.UUIDGenerator")
    private String id;

    @Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @Column(name = "LABEL", length = 90, nullable = false)
    private String label;

    @Transient
    public Result queryResult;

    /**
     * 原始的查询语句
     */
    @Transient
    private String originalQueryString;

    /**
     * 查询语句
     */
    @Transient
    private String queryString;

    /**
     * 数据来源类型
     * 1为SQL
     * 2为表或视图
     * 3为存储过程
     * 4为对象集合
     * 5为json
     */
    @Transient
    private String dataSourceType;

    public Result execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.queryForList(getQueryString());
        return null;
    }

    public void setQueryResult(Result queryResult) {
        this.queryResult = queryResult;
    }

    public String getOriginalQueryString() {
        return originalQueryString;
    }

    public void setOriginalQueryString(String originalQueryString) {
        this.originalQueryString = originalQueryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
