package com.silver.seed.query;

import com.silver.seed.query.QueryResult;
import org.springframework.jdbc.core.JdbcTemplate;

import java.io.Serializable;

/**
 *
 * @author Liaojian
 */
public class Query {

    private Long id;

    private String name;
    
    public QueryResult queryResult;
    /**
     * 原始的查询语句
     */
    private String originalQueryString;
    /**
     * 查询语句
     */
    private String queryString;

    /**
     * 数据来源类型
     * 1为SQL
     * 2为表或视图
     * 3为存储过程
     * 4为对象集合
     * 5为json
     */
    private String dataSourceType;

    public QueryResult execute() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.queryForList(getQueryString());
        return null;
    }

    public QueryResult getQueryResult() {
        return queryResult;
    }

    public void setQueryResult(QueryResult queryResult) {
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
}
