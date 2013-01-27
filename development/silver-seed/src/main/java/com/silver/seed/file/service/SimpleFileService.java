/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.silver.seed.file.service;

import com.silver.seed.exception.SystemRuntimeException;
import com.silver.seed.file.meta.FileMeta;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 *
 * @author liaojian
 */
public class SimpleFileService {    
    
    final Logger logger = LoggerFactory.getLogger(SimpleFileService.class);
    
    public void writeToFile(byte[] bytes, FileMeta fileMeta) {        
        FileOutputStream fos = null;
        try {
            File directory = new File(fileMeta.getDirectory());
            if(!directory.exists()) {
                if(!directory.mkdirs()) {
                    throw new SystemRuntimeException("create directory " + fileMeta.getDirectory() +  " failed!");
                }
            }
            
            File file = new File(fileMeta.getPath());
            if(!file.exists()) {
                if(!file.createNewFile()) {
                    throw new SystemRuntimeException("create file" + fileMeta.getPath() + " failed!");
                }
            }            
            
            logger.info("write file to" + fileMeta.getPath());
            
            fos = new FileOutputStream(file);
            fos.write(bytes);                
        } catch (IOException ex) { 
            logger.error(null, ex);            
            throw new SystemRuntimeException(ex);
        } finally {
            try {                
                if(fos != null) {
                    fos.close();
                }
            } catch (IOException ex) {
                logger.error(null, ex);
                throw new SystemRuntimeException(ex);
            }
        }                
    }
}
