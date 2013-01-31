package com.silver.seed.exception;

/**
 *
 * @author Liaojian
 */
public enum DefaultErrorCode implements ErrorCode{

    DEFAULT_CODE(-1);
    
    private int codeNumber;
    
    private DefaultErrorCode(int codeNumber) {
        this.codeNumber = codeNumber;
    }

    public int getCodeNumber() {
        return codeNumber;
    }    
}
