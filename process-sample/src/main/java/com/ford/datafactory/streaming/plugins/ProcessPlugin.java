package com.ford.datafactory.streaming.plugins;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class ProcessPlugin implements Serializable {
	
	private static final long serialVersionUID = 426430708372946617L;
	
	private static final Logger LOG = LoggerFactory.getLogger(ProcessPlugin.class);

	public List<String> process(Map<String, Object> messageMap, Map<String, Object> param) throws Exception {
		List<String> messageList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> message = null;
		String messageStr = null;
		try {
			message = mapper.readValue(messageMap.get("value").toString(), HashMap.class);
			String fullAddress = message.get("street") + " " + message.get("city") + " " + message.get("state");
			message.put("fullAddress", fullAddress);
			messageStr = mapper.writeValueAsString(message);
			
			messageList.add(messageStr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Invalid message Received in process method ");
		}
		
		return messageList;
	}
	
	public List<String> processWithParam(Map<String, Object> messageMap, Map<String, Object> param) throws Exception {
		
		LOG.info("inside processWithParam param: " + param.toString());
		
		List<String> messageList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> message = null;
		String messageStr = null;
		try {
			message = mapper.readValue(messageMap.get("value").toString(), HashMap.class);
			String fullAddress = message.get("street") + " " + message.get("city") + " " + message.get("state");
			
			if("PROD".equals(param.get("env").toString())) {
				fullAddress = fullAddress.toUpperCase();
			} else {
				fullAddress = fullAddress.toLowerCase();
			}
			
			message.put("fullAddress", fullAddress);
			
			messageStr = mapper.writeValueAsString(message);
			
			messageList.add(messageStr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Invalid message Received in processWithParam method ");
		}
		
		LOG.info("inside processWithParam messages: " + messageList.toString());
		return messageList;
	}

	public List<String> processWithException(Map<String, Object> messageMap, Map<String, Object> param) throws Exception {
		
		LOG.info("inside processWithException param: " + param.toString());
		
		List<String> messageList = new ArrayList<>();
		
		ObjectMapper mapper = new ObjectMapper();
		Map<String,String> message = null;
		String messageStr = null;
		try {
			message = mapper.readValue(messageMap.get("value").toString(), HashMap.class);
			String city = message.get("city");
			String state = message.get("state");
			
			if (city == null || city.trim().isEmpty()) {
				throw new Exception("City field is missing");
			} else if (state == null || state.trim().isEmpty()) {
				throw new Exception("State field is missing");
			}
			
			String fullAddress = message.get("street") + " " + message.get("city") + " " + message.get("state");
			message.put("fullAddress", fullAddress);
			
			messageStr = mapper.writeValueAsString(message);
			
			messageList.add(messageStr);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			throw new Exception("Invalid message Received processWithException method ");
		}
		
		LOG.info("inside processWithParam messages: " + messageList.toString());
		return messageList;
	}

	public void throwException(Map<String, Object> messageMap, Map<String, Object> param) throws Exception
	{
		throw new Exception("process plugin exception test");
	}

	public List<String> convertXmlToJson(Map<String, Object> messageMap, Map<String, Object> param) throws Exception {
		LOG.info("inside convertXmlToJson param: " + param.toString());
		
		List<String> messageList = new ArrayList<>();
		
		try {
			XmlMapper xmlMapper = new XmlMapper();
			ObjectMapper jsonMapper = new ObjectMapper();

			String xmlString = messageMap.get("value").toString();
			LOG.info("XML input: " + xmlString);
			
			// Convert XML to JsonNode
			JsonNode node = xmlMapper.readTree(xmlString);

			// Create a new ObjectNode to wrap the existing node
			JsonNode wrappedNode = jsonMapper.createObjectNode().set("root", node);

			// Convert JsonNode to JSON string
			String jsonString = jsonMapper.writeValueAsString(wrappedNode);
			LOG.info("JSON output: " + jsonString);
			
			messageList.add(jsonString);
		} catch (Exception e) {
			LOG.error("Error converting XML to JSON", e);
			throw new Exception("Error converting XML to JSON: " + e.getMessage());
		}
		
		LOG.info("inside convertXmlToJson messages: " + messageList.toString());
		return messageList;
	}
}