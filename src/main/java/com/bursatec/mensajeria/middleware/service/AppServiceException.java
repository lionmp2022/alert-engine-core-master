package com.bursatec.mensajeria.middleware.service;

/**
 *
 */
public class AppServiceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	public AppServiceException() {
		super("AppServiceException");
	}
	
	/**
	 * @param pMsg
	 */
	public AppServiceException(String pMsg) {
		super(pMsg);
	}

	/**
	 * @param pThrow
	 */
	public AppServiceException(Throwable pThrow) {
		super(pThrow);
	}
}
