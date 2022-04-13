
package mensajeria.impl;

import javax.jws.WebService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bursatec.mensajeria.middleware.service.AppServiceException;
import com.tecnologia.mensajeria.service.MensajeriaService;

import mensajeria.WebServiceMensajeria;



@Component("smsService")
@WebService(targetNamespace = "mensajeria", name = "WebServiceMensajeria")

public class WebServiceMensajeriaImpl implements WebServiceMensajeria {
	
	private static final Logger LOGGER = Logger.getLogger(WebServiceMensajeriaImpl.class);

	@Autowired
	private MensajeriaService mensajeriaService;
	
	

	public void enviarMensajeSms(String destinatario, String mensaje) throws AppServiceException {

		LOGGER.info(" ENVIANDO MENSAJE SMTP " + destinatario);
		mensajeriaService.enviarMensajeSms(destinatario, mensaje);
	}
	
	
	public void enviarMensajeSmtp(String destinatario, String asunto, String mensaje) throws AppServiceException {

		mensajeriaService.enviarMensajeSmtp(destinatario, asunto, mensaje);
		

	}


}
