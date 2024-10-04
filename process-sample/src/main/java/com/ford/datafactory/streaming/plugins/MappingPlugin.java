package com.ford.datafactory.streaming.plugins;

import java.io.Serializable;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.datafactory.streaming.plugins.model.User;
import com.google.api.services.bigquery.model.TableRow;

public class MappingPlugin implements Serializable {
	
	private static final long serialVersionUID = 426430708372946617L;
	private static final Logger LOG = LoggerFactory.getLogger(DestinationPlugin.class);

	public TableRow getMappedData(String message, Map<String, Object> param) {
		LOG.info("mapping in MappingPlugin: " + message);
		
		TableRow tr = new TableRow();
		
		ObjectMapper mapper = new ObjectMapper();
		try {
			User user = mapper.readValue(message, User.class);
			
			tr.set("id", user.getId());
			tr.set("name", user.getName());
			tr.set("age", user.getAge());
			tr.set("street", user.getStreet());
			tr.set("city", user.getCity());
			tr.set("state", user.getState());
			tr.set("fullAddress", user.getFullAddress());
			tr.set("type", user.getType());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return tr;
	}

	public void throwException(String message, Map<String, Object> param) throws Exception
	{
		throw new Exception("mapping plugin exception test");
	}

}