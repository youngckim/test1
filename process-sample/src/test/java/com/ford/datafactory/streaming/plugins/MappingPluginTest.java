package com.ford.datafactory.streaming.plugins;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.google.api.services.bigquery.model.TableRow;

public class MappingPluginTest {
	
	@Test
	public void getDestinationTableTest1() {
		String messageStr = "{\"city\":\"New York\",\"street\":\"20 W 34th St\",\"name\":\"Henry Ford\",\"fullAddress\":\"20 W 34th St New York NY\",\"id\":\"20221203_dd_2\",\"state\":\"NY\",\"type\":\"USER\",\"age\":\"20\"}";
		
		MappingPlugin mp = new MappingPlugin();
		TableRow tr = mp.getMappedData(messageStr, null);
		
		assertEquals("20221203_dd_2", tr.get("id"));
		assertEquals("Henry Ford", tr.get("name"));
		assertEquals("20", tr.get("age"));
		assertEquals("20 W 34th St", tr.get("street"));
		assertEquals("New York", tr.get("city"));
		assertEquals("NY", tr.get("state"));
		assertEquals("USER", tr.get("type"));
	}

}