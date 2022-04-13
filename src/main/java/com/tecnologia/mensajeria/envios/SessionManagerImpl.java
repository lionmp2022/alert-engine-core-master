/*
 */
package com.tecnologia.mensajeria.envios;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.smpp.Data;
import org.smpp.Session;
import org.smpp.TCPIPConnection;
import org.smpp.TimeoutException;
import org.smpp.WrongSessionStateException;
import org.smpp.pdu.BindRequest;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.BindTransciever;
import org.smpp.pdu.PDUException;
import org.smpp.pdu.SubmitSM;
import org.smpp.pdu.SubmitSMResp;
import org.smpp.pdu.UnbindResp;
import org.smpp.pdu.ValueNotSetException;
import org.smpp.pdu.WrongLengthOfStringException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.bursatec.mensajeria.middleware.service.AppServiceException;



@Service
public class SessionManagerImpl implements SessionManager {

	private static final long serialVersionUID = 1L;
	private static Session mSession;
	
	private static final Logger LOG = Logger.getLogger(SessionManagerImpl.class);
	
	@Autowired
	private Environment env;
	
	
	private byte protocolId = 0;
	

	
	/**
	 * @see com.bursatec.mensajeria.middleware.service.smsc.SessionManager#bind()
	 */
	public synchronized BindResponse bind() throws AppServiceException {
		
		BindResponse response = null;
		
		try {
			
			if (mSession != null && mSession.isBound()) {
				LOG.info("Already bound, unbind first.");
				this.unbind();
			}

			BindRequest lRequest  = new BindTransciever();
			
			
			TCPIPConnection connection = new TCPIPConnection(env.getProperty("smsc.hostname"), Integer.valueOf(env.getProperty("smsc.port")));
			
			LOG.info("IpAddress: "+env.getProperty("smsc.hostname")+ " Port: "+env.getProperty("smsc.port"));
			
			connection.setReceiveTimeout(20 * 1000);
			mSession = new Session(connection);

			lRequest.setSystemId(env.getProperty("smsc.systemid") );
			lRequest.setPassword(env.getProperty("smsc.password"));
			lRequest.setSystemType(env.getProperty("smsc.systemtype"));
			lRequest.setInterfaceVersion((byte) 0x34);
			lRequest.setAddressRange(env.getProperty("smsc.address.range"));

			LOG.info("Bind lRequest " + lRequest.debugString());
			response = mSession.bind(lRequest);
			LOG.info("Bind response " + response.debugString());
			LOG.info(" response "+response.toString());
			if (response.getCommandStatus() == Data.ESME_ROK) {
				LOG.info("Bind status: OK");
			}
			else {
				LOG.error("Ocurrio un error al hacer l binding");
				LOG.error("No enlazado para en el envio del mensaje");
			}

			LOG.info(env.getProperty("smsc.systemid") + " -- Bind to the SMPP server on address -> "+ env.getProperty("smsc.hostname")+":"+Integer.valueOf(env.getProperty("smsc.port")));
		} catch (WrongLengthOfStringException lenEx) {
			LOG.error("WrongLengthOfStringException: "+ lenEx,lenEx);
		} catch (ValueNotSetException valueEx) {
			LOG.error("ValueNotSetException: "+ valueEx,valueEx);
		} catch (TimeoutException timeEx) {
			LOG.error("TimeoutException: "+ timeEx,timeEx);
		} catch (PDUException pduEx) {
			LOG.error("PDUException: "+ pduEx,pduEx);
		} catch (WrongSessionStateException sessionEx) {
			LOG.error("WrongSessionStateException: "+ sessionEx,sessionEx);
		} catch (IOException ioEx) {
			LOG.error("IOException: "+ ioEx,ioEx);
		}
		return response; 
	}

	/**
	 * @see com.bursatec.mensajeria.middleware.service.smsc.SessionManager#submit(com.bursatec.mensajeria.vo.MessageVO)
	 */
	public synchronized SubmitSMResp submit(String numberDest, String texto) throws AppServiceException {
		SubmitSM lRequest = new SubmitSM();
		SubmitSMResp response = null;
		try {
			
			lRequest.setServiceType(env.getProperty("smsc.servicetype"));
			lRequest.setSourceAddr(env.getProperty("smsc.source.address"));
			lRequest.setShortMessage(texto);
			lRequest.setProtocolId(protocolId); //no lo encuentro
			lRequest.setPriorityFlag(new Byte(env.getProperty("smsc.priority.flag")));//no lo encuentro
			
			lRequest.setSequenceNumber(0); //message.getSeqNumber() no lo encientro
			long a = System.currentTimeMillis();
			lRequest.setDestAddr(protocolId,protocolId, numberDest);//*********desttone podria ser cero y el otro tambien
			response = mSession.submit(lRequest);
			LOG.info("**************SmsMessage submited**************");
			long b = System.currentTimeMillis();
			LOG.info("Time taken to send and receive response // :" + (b - a));
			
			LOG.info("Message status: "+response.getCommandStatus());
			LOG.info(" response "+response.toString());
			LOG.info(" response "+response.debugString());
			
			if (response.getCommandStatus() == Data.ESME_ROK || response.getCommandStatus() == 81) {
				LOG.info("Mensaje enviado exitosamente");				
			} else {
				LOG.error("Mensaje no pudo ser envíado :" + response.getCommandStatus());
			}
		} catch (ValueNotSetException valueEx) {
			LOG.error("ValueNotSetException: " + valueEx,valueEx);
		} catch (PDUException pduEx) {
			LOG.error("PDUException: " + pduEx,pduEx);
		} catch (WrongSessionStateException sessionEx) {
			LOG.error("WrongSessionStateException: " + sessionEx,sessionEx);
		} catch (TimeoutException timeEx) {
			LOG.error("TimeoutException: " + timeEx,timeEx);
		} catch (IOException ioEx) {
			LOG.error("IOException: " + ioEx,ioEx);
		}
		return response;
	}
	
	/**
	 * @see com.bursatec.mensajeria.middleware.service.smsc.SessionManager#unbind()
	 */
	public synchronized void unbind() {
		try {
			if (mSession == null) {
				LOG.info("Not bound, cannot unbind.");
				return;
			} else if (mSession != null && !mSession.isBound()) {
				LOG.info("Not bound, cannot unbind.");
				return;
			} 

			// send the lRequest
			LOG.info("Going to unbind.");
			if (mSession.getReceiver().isReceiver()) {
				LOG.info("It could take a while to stop the receiver.");
			}
			UnbindResp response = mSession.unbind();
			LOG.info("Unbind response " + response.debugString());

		} catch (Exception e) {
			LOG.error("Unbind operation failed. " + e,e);
		} 
	}



}
