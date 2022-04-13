//@javax.xml.bind.annotation.XmlSchema(namespace="mensajeria")
package com.tecnologia.mensajeria.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.smpp.Data;
import org.smpp.pdu.BindResponse;
import org.smpp.pdu.SubmitSMResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.velocity.VelocityEngineUtils;

import com.bursatec.mensajeria.middleware.service.AppServiceException;
import com.tecnologia.mensajeria.dao.MessageDao;
import com.tecnologia.mensajeria.envios.SessionManager;
import com.tecnologia.mensajeria.model.MessageEntity;
import com.tecnologia.mensajeria.service.MensajeriaService;

@Service
public class MensajeriaServiceImpl implements MensajeriaService {
	private static final Logger LOGGER = Logger.getLogger(MensajeriaServiceImpl.class);

	@Autowired
	private Environment env;

	@Autowired
	private MessageDao messageDao;

	@Autowired
	private SessionManager sessionManager;

	@Autowired
	VelocityEngine velocityEngine;

	private static final String BURSATEC_EMAIL_BODY = "BursatecEmailBody.vm";

	@Transactional
	public void enviarMensajeSms(String string, String string0) throws AppServiceException {

		LOGGER.info(" ENVIANDO MENSAJE SMTP " + string);

		try {
			Date init = new Date();
			BindResponse respBind = sessionManager.bind();
			if (respBind.getCommandStatus() == Data.ESME_ROK || respBind.getCommandStatus() == 81) {
				long a = System.currentTimeMillis();
				SubmitSMResp respSub = sessionManager.submit(string, string0);
				LOGGER.info("**************SmsMessage submited**************");
				long b = System.currentTimeMillis();
				LOGGER.info("Time taken to send and receive response // :" + (b - a));
				Date end = new Date();
				LOGGER.info("--respSub.getSequenceNumber()--" + respSub.getSequenceNumber());
				LOGGER.info("--respSub.getCommandStatus()--" + respSub.getCommandStatus());

				this.saveSMS(string, string0, respSub.getCommandStatus(), init, end);

			}
		} catch (Exception e) {
			LOGGER.error("ocurrio un error ", e);
		} finally {
			sessionManager.unbind();
		}
	}

	@Transactional
	public void enviarMensajeSmtp(String receiver, String subject, String body) throws AppServiceException {

		String host = env.getProperty("mensajeria.app.emailHostname");
		String fromEmail = env.getProperty("mensajeria.app.emailFrom");
		String nameEmail = env.getProperty("mensajeria.app.emailName");

		LOGGER.info("emailFromemailFrom" + fromEmail);

		HtmlEmail email = new HtmlEmail();

		try {
			long a = System.currentTimeMillis();
			email.setHostName(host);
			email.setFrom(fromEmail, nameEmail);
			email.addTo(receiver);
			email.setSubject(subject);
			HashMap model = new HashMap();
			model.put("mensaje", body.replaceAll("\n", "<br>"));
			email.setMsg(this.geContentFromTemplate(model));
			email.send();

			LOGGER.debug("**************EmailMessage submited**************");

			long b = System.currentTimeMillis();
			LOGGER.debug("Time taken to send and receive response // :" + (b - a));

			this.saveMessage(receiver, subject, body);

		} catch (EmailException emailEx) {
			LOGGER.error("EmailException: " + emailEx, emailEx);
			throw new AppServiceException(emailEx);
		}

	}

	private void saveMessage(String receiver, String subject, String body) {
		MessageEntity messageEntity = new MessageEntity();
		messageEntity.setEmailAdress(receiver);
		messageEntity.setMessageBody(body);
		messageEntity.setSysSenderId(0);
		messageEntity.setInsertTime(new Date());
		messageEntity.setDeliveryTime(new Date());
		messageDao.save(messageEntity);
	}

	private void saveSMS(String receiver, String msg, int responseID, Date initS, Date endS) {
		MessageEntity lSmsMessage = new MessageEntity();
		lSmsMessage.setSmsAddress(receiver);
		lSmsMessage.setSmsTon(Integer.valueOf(env.getProperty("smsc.address.ton")));
		lSmsMessage.setSmsNpi(Integer.valueOf(env.getProperty("smsc.address.npi")));
		lSmsMessage.setMessageBody(msg);
		lSmsMessage.setPriorityFlag(Integer.valueOf(env.getProperty("smsc.priority.flag")));

		lSmsMessage.setResponseId(responseID);
		// lSmsMessage.setSysSenderId(sysSenderId);
		lSmsMessage.setSeqNumber(Integer.valueOf(env.getProperty("smsc.address.ton")));
		lSmsMessage.setStatusMessage(Integer.valueOf(responseID));
		lSmsMessage.setInsertTime(initS);
		lSmsMessage.setDeliveryTime(endS);
		LOGGER.debug("SmsMessage :" + lSmsMessage.toString());
		messageDao.save(lSmsMessage);
	}



	public String geContentFromTemplate(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		try {
			content.append(VelocityEngineUtils.mergeTemplateIntoString(velocityEngine, BURSATEC_EMAIL_BODY, model));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content.toString();
	}

}
