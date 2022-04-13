package com.mx.bmv.tecnologia.sms;

import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

public class MySoapHandler implements SOAPHandler<SOAPMessageContext>
{



	public boolean handleFault(SOAPMessageContext context) {
		// TODO Auto-generated method stub
		return false;
	}

	public void close(MessageContext context) {
		// TODO Auto-generated method stub
		
	}

	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean handleMessage(SOAPMessageContext context) {
		try
	    {
	      SOAPMessage message = context.getMessage();
	      // I haven't tested this
	      message.getSOAPHeader().setPrefix("soapenv");
	      context.setMessage(message);
	    }
	    catch (SOAPException e)
	    {
	      // Handle exception
	    }

	    return true;
	}


}
