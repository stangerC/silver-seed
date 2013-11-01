package com.silver.seed.query.showcase.entity.list;

import java.util.List;

/**
 *
 * @author Liaojian
 */
public class ListJqGridMeta extends ListMeta{    
    private List<Column> columns;
    
    public String getColNames() {
        String colNames = null;
        if(columns != null && columns.size() > 0) {
            StringBuilder buffer = new StringBuilder();
            for(Column column : columns) {
                buffer.append("'").append(column.getName()).append("',");
            }
            buffer.delete(buffer.length() - 1, buffer.length());
        }
        return colNames;
    }    
    
    public String getColModel() {
        String colModel = null;
        
        if(columns != null && columns.size() > 0) {
            StringBuilder buffer = new StringBuilder();
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
        
        return colModel;
    }
    
    class Column {
        private String name;
        private String index;
        private Integer width;
        private String align;
        private boolean sortable;

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
    }
}
