package com.silver.seed.exception;

import com.silver.seed.exception.CodeException;

/**
 * 系统运行时异常。继承自RuntimeException，是一个UncheckedException。对于系统底层抛出的、无法挽救的
 * CheckedException异常，如SQLException，IOException，使用本异常或本异常的子类进行封装，然后抛出。
 * 这样在各个调用层次上，无需再进行捕获或显示抛出。可以在一个统一的层次再进行处理，比如View层。
 * 
 * @author liaojian
 *
 */
@SuppressWarnings("serial")
public class SystemRuntimeException extends RuntimeException implements CodeException{
	/**
	 * 错误代码
	 */
	private String code;
	/**
	 * 默认构造函数 
	 */
	public SystemRuntimeException() {		
	}
	/**
	 * 需要错误代码参数的构造函数
	 * @param code
	 * 		异常的错误代码
	 */
	public SystemRuntimeException(String code) {
		this.code = code;
	}
	/**
	 * 带有错误代码、原因异常的构造函数
	 * @param code
	 * 		异常的错误代码
	 * @param cause
	 * 		原因异常
	 */
	public SystemRuntimeException(String code, Throwable cause) {
		super(cause);
		this.code = code;
	}
	/**
	 * 需要原因异常的构造函数
	 * @param cause
	 * 		原因异常
	 */
	public SystemRuntimeException(Throwable cause) {
		super(cause);
	}
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
