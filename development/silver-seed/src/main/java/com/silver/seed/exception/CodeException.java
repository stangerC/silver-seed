package com.silver.seed.exception;
/**
 * 错误代码异常
 * @author liaojian
 *
 */
public interface CodeException {
	/**
	 * 获取错误代码
	 * @return
	 * 		异常的错误代码
	 */
	public String getCode();
	/**
	 * 设置错误代码
	 * @param code
	 * 		异常的错误代码
	 */
	public void setCode(String code);
}
