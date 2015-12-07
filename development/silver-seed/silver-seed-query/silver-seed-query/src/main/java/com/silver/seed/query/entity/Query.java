package com.silver.seed.query.entity;

import com.silver.seed.core.entity.Entity;
import com.silver.seed.query.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.persistence.*;
import java.util.Iterator;
import java.util.List;


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

    @javax.persistence.Column(name = "NAME", length = 50, nullable = false)
    private String name;

    @javax.persistence.Column(name = "LABEL", length = 90, nullable = false)
    private String label;

    @OneToMany(targetEntity = com.silver.seed.query.entity.Table.class, mappedBy = "query", fetch = FetchType.EAGER)
    private List<com.silver.seed.query.entity.Table> tables;

    @OneToMany(targetEntity = com.silver.seed.query.entity.JoinColumns.class, mappedBy = "query", fetch = FetchType.EAGER)
    private List<com.silver.seed.query.entity.JoinColumns> joinColumnses;

    @OneToMany(targetEntity = Condition.class, mappedBy = "query", fetch = FetchType.EAGER)
    private List<Condition> conditions;

    @OneToMany(targetEntity = Column.class, mappedBy = "query", fetch = FetchType.EAGER)
    private List<Column> columns;

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

    public void setId(String id) {
        this.id = id;
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

    public List<com.silver.seed.query.entity.Table> getTables() {
        return tables;
    }

    public void setTables(List<com.silver.seed.query.entity.Table> tables) {
        this.tables = tables;
    }

    public List<com.silver.seed.query.entity.JoinColumns> getJoinColumnses() {
        return joinColumnses;
    }

    public void setJoinColumnses(List<com.silver.seed.query.entity.JoinColumns> joinColumnses) {
        this.joinColumnses = joinColumnses;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public String generateSql() {
        //todo，增加对没有表的异常处理

        StringBuilder sb = new StringBuilder("select ");
        for(Column column : getColumns()) {
            sb.append(column.generateSql());
        }

        sb.append(" from ");
        for(com.silver.seed.query.entity.Table table : getTables()) {
            sb.append(table.generateSql()).append(" ");
        }

        sb.append(" where ");
        for(Condition condition : getConditions()) {
            sb.append(condition.generateSql()).append(" and ");
        }
        sb.delete(sb.length() - 5, sb.length() - 1);

        return sb.toString();
    }
}
