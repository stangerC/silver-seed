package com.silver.seed.file.meta.factory;

import com.silver.seed.exception.SystemRuntimeException;
import com.silver.seed.file.meta.SimpleFileMeta;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.UUID;
import java.util.logging.Level;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Liaojian
 */
public class SimpleFileMetaFactory extends BaseFileMetaFactory<SimpleFileMeta, String> {

    final Logger logger = LoggerFactory.getLogger(SimpleFileMetaFactory.class);
    private DirectoryStyle directoryStyle;
    private SavedNameStyle savedNameStyle;
    private String baseDirectory;

    public SimpleFileMetaFactory(DirectoryStyle directoryStyle, 
            SavedNameStyle savedNameStyle, String baseDirectory) {
        this.directoryStyle = directoryStyle;
        this.savedNameStyle = savedNameStyle;
        this.baseDirectory = (baseDirectory == null ? "" : baseDirectory);
    }

    @Override
    protected SimpleFileMeta prepareFileMeta(SimpleFileMeta simpleFileMeta, String name) {
        simpleFileMeta.setName(name);
        StringBuilder directoryBuilder = new StringBuilder(this.baseDirectory);
        
        switch (directoryStyle) {
            case DATE:
                directoryBuilder.append(File.separator);
                Calendar calendar = Calendar.getInstance();
                directoryBuilder.append(calendar.get(Calendar.YEAR)).append(File.separator)
                        .append(calendar.get(Calendar.MONTH)).append(File.separator)
                        .append(calendar.get(Calendar.DATE)).append(File.separator);               
                break;
            case FILE_NAME:
                directoryBuilder.append(File.separator).append(simpleFileMeta.getName());                
                break;
            case FILE_NAME_ISO_8859_1:
                try {
                    directoryBuilder.append(File.separator).append(
                            new String(simpleFileMeta.getName().getBytes(), "ISO-8859-1"));                    
                } catch (UnsupportedEncodingException ex) {
                    java.util.logging.Logger.getLogger(SimpleFileMetaFactory.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
        }
        simpleFileMeta.setDirectory(directoryBuilder.toString());
        
        switch (savedNameStyle) {
            case UUID:
                simpleFileMeta.setPath(directoryBuilder
                        .append(UUID.randomUUID())
                        .append(simpleFileMeta.getExtension() == null ? "" : simpleFileMeta.getExtension())
                        .toString());
                break;
            case FILE_NAME:
                simpleFileMeta.setPath(directoryBuilder.append(simpleFileMeta.getName()).toString());
                break;
            case FILE_NAME_ISO8859_1:
                try {
                    simpleFileMeta.setPath(directoryBuilder.append(
                            new String(simpleFileMeta.getName().getBytes(), "ISO-8859-1")).toString());
                } catch (UnsupportedEncodingException ex) {
                    throw new SystemRuntimeException(ex);
                }
                break;
        }        

        return simpleFileMeta;
    }
}
