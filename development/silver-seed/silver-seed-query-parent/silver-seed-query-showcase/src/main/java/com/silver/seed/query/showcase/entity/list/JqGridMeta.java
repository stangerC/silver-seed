package com.silver.seed.query.showcase.entity.list;

import com.silver.seed.query.entity.Column;
import com.silver.seed.query.view.ViewMeta;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Liaojian
 */
public class JqGridMeta implements ViewMeta{    
    private String caption;
    private String dataType;
    private String mtype;
    private String url;
    
    private List<Column> columns = new ArrayList<Column>();
    
    public String getColNames() {
        StringBuilder buffer = new StringBuilder();
        if(columns != null && columns.size() > 0) {
            buffer = new StringBuilder();
            for(Column column : columns) {
                buffer.append("'").append(column.getName()).append("',");
            }
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        return buffer.toString();
    }    
    
    public String getColModel() {
        StringBuilder buffer = new StringBuilder();
        
        if(columns != null && columns.size() > 0) {
            buffer = new StringBuilder();
            for(Column column : columns) {
                buffer.append("{name:'").append(column.getName()).append("',index:'")
                        .append(column.getIndex()).append("'");
                if(column.getWidth() != null) {
                    buffer.append(",width:").append(column.getWidth());
                }
                if(column.getAlign() != null) {
                    buffer.append(", align:'").append(column.getAlign()).append("'");
                }
                buffer.append("},");
            }
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        
        return buffer.toString();
    }        
    
    public List<Column> addColumn(Column column) {
        columns.add(column);
        return columns;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }        
}
