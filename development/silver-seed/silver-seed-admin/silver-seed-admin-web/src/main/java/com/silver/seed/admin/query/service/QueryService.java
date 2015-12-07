package com.silver.seed.admin.query.service;

import com.silver.seed.query.entity.Condition;
import com.silver.seed.query.entity.JoinColumns;
import com.silver.seed.query.entity.Query;
import com.silver.seed.query.entity.Table;
import com.silver.seed.query.entity.Column;
import com.silver.seed.query.entity.ColumnVO;
import com.silver.seed.query.repository.ColumnPairRepository;
import com.silver.seed.query.repository.ColumnRepository;
import com.silver.seed.query.repository.ConditionRepository;
import com.silver.seed.admin.query.repository.QueryRepository;
import com.silver.seed.query.repository.TableRepository;
import com.silver.wheel.common.exception.CodedRuntimeException;
import org.springframework.jdbc.support.DatabaseMetaDataCallback;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Liaojian on 2014/10/7.
 */
@Service("queryService")
public class QueryService {
    @Resource
    private QueryRepository queryRepository;

    @Resource
    private TableRepository tableRepository;

    @Resource
    private ColumnPairRepository columnPairRepository;

    @Resource
    private ConditionRepository conditionRepository;

    @Resource
    private ColumnRepository columnRepository;

    @Resource
    private DataSource dataSource;

    public QueryRepository getQueryRepository() {
        return queryRepository;
    }

    public void setQueryRepository(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    public TableRepository getTableRepository() {
        return tableRepository;
    }

    public void setTableRepository(TableRepository tableRepository) {
        this.tableRepository = tableRepository;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public ColumnPairRepository getColumnPairRepository() {
        return columnPairRepository;
    }

    public void setColumnPairRepository(ColumnPairRepository columnPairRepository) {
        this.columnPairRepository = columnPairRepository;
    }

    public ConditionRepository getConditionRepository() {
        return conditionRepository;
    }

    public void setConditionRepository(ConditionRepository conditionRepository) {
        this.conditionRepository = conditionRepository;
    }

    public ColumnRepository getColumnRepository() {
        return columnRepository;
    }

    public void setColumnRepository(ColumnRepository columnRepository) {
        this.columnRepository = columnRepository;
    }

    public List<String> getSchemas() {
        List<String> schemas = null;
        try {
            schemas = (List<String>)JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
                @Override
                public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException, MetaDataAccessException {
                    List<String> schemas = new ArrayList<String>();
                    ResultSet rs = databaseMetaData.getSchemas();
                    while (rs.next()) {
                        String schema = rs.getString(1);
                        schemas.add(schema);
                    }
                    return schemas;
                }
            });
        } catch (MetaDataAccessException e) {
            throw new CodedRuntimeException(e);
        }
        return schemas;
    }

    public List<String> getTablesNames(String schema) {
        List<String> tables = null;
        try {
             tables = ( List<String>) JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
                 @Override
                 public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException, MetaDataAccessException {
                     List<String> tables = new ArrayList<String>();
                     String catalog = databaseMetaData.getConnection().getCatalog();
                     String schema = null;//databaseMetaData.getConnection().getSchema();
                     ResultSet rs = databaseMetaData.getTables(catalog, schema ,"%", new String[] {"TABLE"});
                     ResultSetMetaData rsmd = rs.getMetaData();
                     while(rs.next()) {
                         tables.add(rs.getString("TABLE_NAME"));
                     }
                     return tables;
                 }
             });
        } catch (MetaDataAccessException e){
            throw new CodedRuntimeException(e);
        }
        return tables;
    }

    public Boolean tableExists(final String tableName) {
        Boolean exists = null;
        try {
            exists = (Boolean) JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
                @Override
                public Object processMetaData(DatabaseMetaData databaseMetaData) throws SQLException, MetaDataAccessException {
                    String catalog = databaseMetaData.getConnection().getCatalog();
                    String schema = null;//databaseMetaData.getConnection().getSchema();
                    ResultSet rs = databaseMetaData.getTables(catalog, schema ,tableName, new String[] {"TABLE"});
                    ResultSetMetaData rsmd = rs.getMetaData();
                    if(rs.next()) {
                        return new Boolean(true);
                    }
                    return new Boolean(false);
                }
            });
        } catch (MetaDataAccessException e){
            throw new CodedRuntimeException(e);
        }
        return exists;
    }

    public List<ColumnVO> getColumns(final String table) {
        List<ColumnVO> columns = null;

        try {
            columns = (List<ColumnVO>)JdbcUtils.extractDatabaseMetaData(dataSource, new DatabaseMetaDataCallback() {
                @Override
                public Object processMetaData(DatabaseMetaData dbmd) throws SQLException, MetaDataAccessException {
                    String catalog = dbmd.getConnection().getCatalog();
                    String schema = null;
                    ResultSet rs = dbmd.getTables(catalog, schema, table, new String[] {"TABLE"});
                    List<ColumnVO> columns = new ArrayList<ColumnVO>();
                    if(rs.next()) {
                        ResultSetMetaData rsmd = rs.getMetaData();
                        for(int i = 1; i <= rsmd.getColumnCount(); i ++) {
                            ColumnVO column = new ColumnVO();
                            column.setName(rsmd.getColumnName(i));
                            column.setType(rsmd.getColumnType(i));
                            columns.add(column);
                        }
                    }
                    return columns;
                }
            });
        } catch (MetaDataAccessException e) {
            throw new CodedRuntimeException(e);
        }

        return columns;
    }

    public void saveTableAndJoindedColumns(List<Table> tables, List<JoinColumns> columns) {
        getTableRepository().save(tables);
        getColumnPairRepository().save(columns);
    }

    public void saveTableAndJoindedColumns(String queryId, List<Table> tables, List<JoinColumns> columns) {
        Query query = getQueryRepository().findOne(queryId);

        for(Table table : tables) {
            table.setQuery(query);
        }
        getTableRepository().save(tables);
        getColumnPairRepository().save(columns);
    }

    public void saveConditions(List<Condition> conditions) {
        getConditionRepository().save(conditions);
    }

    public void saveColumns(List<Column> columns) {
        getColumnRepository().save(columns);
    }

    public void saveQuery(Query query) {
        getQueryRepository().save(query);
    }

    public String generateSQL(String queryId) {
        Query query = getQueryRepository().findOne(queryId);
        return query.generateSql();
    }
}
