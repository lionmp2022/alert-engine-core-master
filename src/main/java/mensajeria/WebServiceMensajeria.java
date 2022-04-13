package mensajeria;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

import com.bursatec.mensajeria.middleware.service.AppServiceException;

@WebService
@SOAPBinding(style = Style.RPC)
public interface WebServiceMensajeria {
	
	public void enviarMensajeSms (
			@WebParam(name = "destinatario", header = false) String destinatario,
			@WebParam(name = "mensaje", header = false) String mensaje)
					 throws AppServiceException;

	
	public void enviarMensajeSmtp (
			@WebParam(name = "destinatario", header = false) String destinatario,
			@WebParam(name = "asunto", header = false) String asunto,
			@WebParam(name = "mensaje", header = false) String mensaje)
					 throws AppServiceException;
	
}
