
package com.tecnologia.mensajeria.envios;

import java.io.Serializable;

import org.smpp.pdu.BindResponse;
import org.smpp.pdu.SubmitSMResp;

import com.bursatec.mensajeria.middleware.service.AppServiceException;



public interface SessionManager extends Serializable {

	public BindResponse bind() throws AppServiceException;
	
	
	public SubmitSMResp submit(String numberDest, String texto) throws AppServiceException;
	
	public void unbind();
	
}
