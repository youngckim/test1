package com.ford.datafactory.streaming.plugins;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DestinationPlugin implements Serializable {
	
	private static final Logger LOG = LoggerFactory.getLogger(DestinationPlugin.class);
	
	private static final long serialVersionUID = 426430708372946617L;

	public Map<String, String> getDestinationTable(String messageStr, Map<String, Object> param) {
		
		LOG.info("message in DestinationPlugin: " + messageStr);
		
		ObjectMapper mapper = new ObjectMapper();
		Map messageMap = null;
		try {
			messageMap = mapper.readValue(messageStr, HashMap.class);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		
		int age = Integer.parseInt(String.valueOf(messageMap.get("age")));
		String state = String.valueOf(messageMap.get("state"));
		
		List<String> easternStates = new ArrayList<>();
		easternStates.add("NY");
		easternStates.add("NJ");
		easternStates.add("PA");
		
		List<String> westernStates = new ArrayList<>();
		westernStates.add("CA");
		westernStates.add("WA");
		
		Map<String, String> destinationTable = new HashMap<>();
		destinationTable.put("projectId", "prj-dfad-18-ingtool-d-18");
		destinationTable.put("dataset", "streaming_ingesttool");
		
		if (age <= 25 && easternStates.contains(state)) {
			destinationTable.put("table", "users-young-east");
		} else if (age > 25 && easternStates.contains(state)) {
			destinationTable.put("table", "users-old-east");
		} else if (age <= 25 && westernStates.contains(state)) {
			destinationTable.put("table", "users-young-west");
		} else if (age > 25 && westernStates.contains(state)) {
			destinationTable.put("table", "users-old-west");
		} else {
			destinationTable.put("table", "users");
		}
		
		return destinationTable;
	}

	
	public Map<String, String> getDestinationTableWithPP(String messageStr, Map<String, Object> param) {
		
		LOG.info("message in DestinationPlugin: " + messageStr);
		
		ObjectMapper mapper = new ObjectMapper();
		Map messageMap = null;
		try {
			messageMap = mapper.readValue(messageStr, HashMap.class);
		} catch (IOException e1) {
			e1.printStackTrace();
			return null;
		}
		
		int age = Integer.parseInt(String.valueOf(messageMap.get("age")));
		String state = String.valueOf(messageMap.get("state"));
		
		List<String> easternStates = new ArrayList<>();
		easternStates.add("NY");
		easternStates.add("NJ");
		easternStates.add("PA");
		
		List<String> westernStates = new ArrayList<>();
		westernStates.add("CA");
		westernStates.add("WA");
		
		Map<String, String> destinationTable = new HashMap<>();
		destinationTable.put("projectId", "prj-dfad-18-ingtool-d-18");
		destinationTable.put("dataset", "streaming_ingesttool");
		
		if (age <= 25 && param.get("env").equals("PROD") && easternStates.contains(state)) {
			destinationTable.put("table", "users-young-east-prod");
		} else if (age <= 25 && easternStates.contains(state)) {
			destinationTable.put("table", "users-young-east");
		} else if (age > 25 && easternStates.contains(state)) {
			destinationTable.put("table", "users-old-east");
		} else if (age <= 25 && westernStates.contains(state)) {
			destinationTable.put("table", "users-young-west");
		} else if (age > 25 && westernStates.contains(state)) {
			destinationTable.put("table", "users-old-west");
		} else {
			destinationTable.put("table", "users");
		}
		
		return destinationTable;
	}

	public void throwException(String messageStr, Map<String, Object> param) throws Exception
	{
		throw new Exception("destination plugin exception test");
	}
}