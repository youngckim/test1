package com.ford.datafactory.streaming.plugins;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

public class DestinationPluginTest {
	
	@Test
	public void getDestinationTableTest1() {
		String messageStr = "{\"city\":\"New York\",\"street\":\"20 W 34th St\",\"name\":\"Henry Ford\",\"fullAddress\":\"20 W 34th St New York NY\",\"id\":\"20221203_dd_2\",\"state\":\"NY\",\"type\":\"USER\",\"age\":\"20\"}";
		
		DestinationPlugin dp = new DestinationPlugin();
		Map<String, String> destinationTable = dp.getDestinationTable(messageStr, null);
		
		assertEquals("prj-dfad-18-ingtool-d-18", destinationTable.get("projectId"));
		assertEquals("streaming_ingesttool", destinationTable.get("dataset"));
		assertEquals("users-young-east", destinationTable.get("table"));
	}

	@Test
	public void getDestinationTableTest2() {
		String messageStr = "{\"city\":\"New York\",\"street\":\"20 W 34th St\",\"name\":\"Henry Ford\",\"fullAddress\":\"20 W 34th St New York NY\",\"id\":\"20221203_dd_1\",\"state\":\"NY\",\"type\":\"USER\",\"age\":\"45\"}";
		
		DestinationPlugin dp = new DestinationPlugin();
		Map<String, String> destinationTable = dp.getDestinationTable(messageStr, null);
		
		assertEquals("prj-dfad-18-ingtool-d-18", destinationTable.get("projectId"));
		assertEquals("streaming_ingesttool", destinationTable.get("dataset"));
		assertEquals("users-old-east", destinationTable.get("table"));
	}

	@Test
	public void getDestinationTableTest3() {
		String messageStr = "{\"city\":\"Seattle\",\"street\":\"85 Pike St\",\"name\":\"Nicola Tesla\",\"fullAddress\":\"85 Pike St Seattle WA\",\"id\":\"20221203_dd_3\",\"state\":\"WA\",\"type\":\"USER\",\"age\":\"22\"}";
		
		DestinationPlugin dp = new DestinationPlugin();
		Map<String, String> destinationTable = dp.getDestinationTable(messageStr, null);
		
		assertEquals("prj-dfad-18-ingtool-d-18", destinationTable.get("projectId"));
		assertEquals("streaming_ingesttool", destinationTable.get("dataset"));
		assertEquals("users-young-west", destinationTable.get("table"));
	}
	
	@Test
	public void getDestinationTableTest4() {
		String messageStr = "{\"city\":\"Seattle\",\"street\":\"85 Pike St\",\"name\":\"Nicola Tesla\",\"fullAddress\":\"85 Pike St Seattle WA\",\"id\":\"20221203_dd_3\",\"state\":\"WA\",\"type\":\"USER\",\"age\":\"55\"}";
		
		DestinationPlugin dp = new DestinationPlugin();
		Map<String, String> destinationTable = dp.getDestinationTable(messageStr, null);
		
		assertEquals("prj-dfad-18-ingtool-d-18", destinationTable.get("projectId"));
		assertEquals("streaming_ingesttool", destinationTable.get("dataset"));
		assertEquals("users-old-west", destinationTable.get("table"));
	}
	
	@Test
	public void getDestinationTableTest5() {
		String messageStr = "{\"id\":\"20221203_dd_1\",\"name\":\"R. R. Martin\",\"age\":\"74\",\"street\":\"1700 NE 63rd St\",\"city\":\"Oklahoma City\",\"state\":\"OK\",\"type\":\"USER\"}";
		
		DestinationPlugin dp = new DestinationPlugin();
		Map<String, String> destinationTable = dp.getDestinationTable(messageStr, null);
		
		assertEquals("prj-dfad-18-ingtool-d-18", destinationTable.get("projectId"));
		assertEquals("streaming_ingesttool", destinationTable.get("dataset"));
		assertEquals("users", destinationTable.get("table"));
	}
	
}