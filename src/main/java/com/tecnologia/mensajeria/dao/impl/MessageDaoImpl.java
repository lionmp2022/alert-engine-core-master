package com.tecnologia.mensajeria.dao.impl;



import org.springframework.stereotype.Repository;

import com.tecnologia.mensajeria.dao.MessageDao;
import com.tecnologia.mensajeria.model.MessageEntity;


@Repository
public class MessageDaoImpl extends BaseDaoImpl<MessageEntity> implements MessageDao{

	
	public MessageDaoImpl() {
		super(MessageEntity.class);
	}

	
	public MessageEntity save(MessageEntity messageEntity) {
		return this.insertar(messageEntity);
	}
	
	
}
