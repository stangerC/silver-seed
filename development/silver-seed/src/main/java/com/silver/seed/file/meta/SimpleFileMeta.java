package com.silver.seed.file.meta;

/**
 *
 * @author Liaojian
 */
public class SimpleFileMeta implements FileMeta<String>{
    
    private String name;
    
    private String path;
    
    private long size;      
    
    private String directory;     
    
    private String extension;
    
    public String getName() {
        return name;
    }
    
    public String getPath() {
        return path;
    }

    public long getSize() {
        return size;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setSize(long size) {
        this.size = size;
    }     

    public String getDirectory() {
        return directory;
    }

    public void setDirectory(String directory) {
        this.directory = directory;
    }
    
    public String getExtension() {
        if(extension == null) {
            int beginIndex = name.lastIndexOf(".");
            if(beginIndex >= 0) {
                extension = name.substring(beginIndex, name.length());
            }
        }
        
        return extension;
    }
}
