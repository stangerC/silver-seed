package com.silver.seed.query.showcase.service;

import com.silver.seed.query.Query;
import com.silver.seed.query.Result;
import com.silver.seed.query.showcase.repository.QueryRepository;
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
    private DataSource dataSource;

    public QueryRepository getQueryRepository() {
        return queryRepository;
    }

    public void setQueryRepository(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
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
}
