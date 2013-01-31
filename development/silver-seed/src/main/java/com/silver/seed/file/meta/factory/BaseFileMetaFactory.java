package com.silver.seed.file.meta.factory;

import com.silver.seed.exception.CodeRuntimeException;
import com.silver.seed.file.meta.FileMeta;
import com.silver.seed.pattern.factory.VariableFactory;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Liaojian
 */
public abstract class BaseFileMetaFactory<T extends FileMeta, V extends String> implements VariableFactory<T, V> {
    protected abstract T prepareFileMeta(T fileMeta, V variable);
        
    protected T createFileMeta() {
        
        T fileMeta = null;
        
        Class<T> fileMetaClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        try {
            fileMeta = fileMetaClass.newInstance();
        } catch (InstantiationException ex) {
            throw new CodeRuntimeException(ex);
        } catch (IllegalAccessException ex) {
            throw new CodeRuntimeException(ex);
        }
        
        return fileMeta;
    }
    
    public T create(V variable) {        
        T fileMeta = createFileMeta();
        fileMeta = prepareFileMeta(fileMeta, variable);
        return fileMeta;
    }
}
