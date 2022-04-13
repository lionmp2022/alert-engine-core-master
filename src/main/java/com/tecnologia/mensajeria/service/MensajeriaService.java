package com.tecnologia.mensajeria.service;

import com.bursatec.mensajeria.middleware.service.AppServiceException;

public interface MensajeriaService {
	
	public void enviarMensajeSmtp(String receiver, String subject, String body) throws AppServiceException;
	
	public void enviarMensajeSms(String numberRec, String messageSub) throws AppServiceException;
	
}
