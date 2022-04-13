package com.tecnologia.mensajeria.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "MESSAGE")
public class MessageEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3348588727053763644L;


	@Id
	@Column(name = "MESSAGE_ID")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SMS_MESSAGE_SEQ")
	private Long messageId;

	@Column(name = "SMS_ADDRESS")
	private String smsAddress;
	
	@Column(name = "SMS_TON")
	private Integer smsTon;
	
	@Column(name = "SMS_NPI")
	private Integer smsNpi;
	
	@Column(name = "INSERT_TIME")
	private Date insertTime;
	
	@Column(name = "EXPIRITY_TIME")
	private Date expirityTime;
	
	@Column(name = "DELIVERY_TIME")
	private Date deliveryTime;
	
	@Column(name = "SCHEDULE_TIME")
	private Date scheduleTime;
	
	@Column(name = "SEQ_NUMBER")
	private Integer seqNumber;
	
	@Column(name = "SYS_SENDER_ID")
	private Integer sysSenderId;

	
	@Column(name = "RESPONSE_ID")
	private Integer responseId;
	
	@Column(name = "MESSAGE_BODY")
	private String messageBody;
	
	@Column(name = "PRIORITY_FLAG")
	private Integer priorityFlag;
	
	@Column(name = "STATUS_MESSAGE")
	private Integer statusMessage;

	@Column(name = "EMAIL_ADDRESS")
	private String emailAdress;	

	@Column(name = "SUBJECT")
	private String subject;	

	@Column(name = "LOGIN_NAME")
	private String loginName;

	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSmsAddress() {
		return smsAddress;
	}

	public void setSmsAddress(String smsAddress) {
		this.smsAddress = smsAddress;
	}

	public Integer getSmsTon() {
		return smsTon;
	}

	public void setSmsTon(Integer smsTon) {
		this.smsTon = smsTon;
	}

	public Integer getSmsNpi() {
		return smsNpi;
	}

	public void setSmsNpi(Integer smsNpi) {
		this.smsNpi = smsNpi;
	}

	public Date getInsertTime() {
		return insertTime;
	}

	public void setInsertTime(Date insertTime) {
		this.insertTime = insertTime;
	}

	public Date getExpirityTime() {
		return expirityTime;
	}

	public void setExpirityTime(Date expirityTime) {
		this.expirityTime = expirityTime;
	}

	public Date getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(Date deliveryTime) {
		this.deliveryTime = deliveryTime;
	}

	public Date getScheduleTime() {
		return scheduleTime;
	}

	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}

	public Integer getSeqNumber() {
		return seqNumber;
	}

	public void setSeqNumber(Integer seqNumber) {
		this.seqNumber = seqNumber;
	}

	public Integer getSysSenderId() {
		return sysSenderId;
	}

	public void setSysSenderId(Integer sysSenderId) {
		this.sysSenderId = sysSenderId;
	}

	public Integer getResponseId() {
		return responseId;
	}

	public void setResponseId(Integer responseId) {
		this.responseId = responseId;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public Integer getPriorityFlag() {
		return priorityFlag;
	}

	public void setPriorityFlag(Integer priorityFlag) {
		this.priorityFlag = priorityFlag;
	}

	public Integer getStatusMessage() {
		return statusMessage;
	}

	public void setStatusMessage(Integer statusMessage) {
		this.statusMessage = statusMessage;
	}

	public String getEmailAdress() {
		return emailAdress;
	}

	public void setEmailAdress(String emailAdress) {
		this.emailAdress = emailAdress;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}	
	
	/*
	 
MESSAGE_ID            NUMBER        
SMS_ADDRESS           CHAR(18)      
SMS_TON               NUMBER(1)     
SMS_NPI               NUMBER(1)     
INSERT_TIME           DATE          
EXPIRITY_TIME         DATE          
DELIVERY_TIME         DATE          
SCHEDULE_TIME         DATE          
SEQ_NUMBER            NUMBER(3)     
SYS_SENDER_ID         NUMBER        
RESPONSE_ID           NUMBER        
MESSAGE_BODY          VARCHAR2(350) 
PRIORITY_FLAG         NUMBER(1)     
STATUS_MESSAGE        NUMBER(2)     
EMAIL_ADDRESS         VARCHAR2(50)  
SUBJECT               VARCHAR2(80)  
LOGIN_NAME            VARCHAR2(20)  

	 * */

	@Override
	public String toString() {
		return "MessageEntity{" +
				"messageId=" + messageId +
				", smsAddress='" + smsAddress + '\'' +
				", smsTon=" + smsTon +
				", smsNpi=" + smsNpi +
				", insertTime=" + insertTime +
				", expirityTime=" + expirityTime +
				", deliveryTime=" + deliveryTime +
				", scheduleTime=" + scheduleTime +
				", seqNumber=" + seqNumber +
				", sysSenderId=" + sysSenderId +
				", responseId=" + responseId +
				", messageBody='" + messageBody + '\'' +
				", priorityFlag=" + priorityFlag +
				", statusMessage=" + statusMessage +
				", emailAdress='" + emailAdress + '\'' +
				", subject='" + subject + '\'' +
				", loginName='" + loginName + '\'' +
				'}';
	}
}
