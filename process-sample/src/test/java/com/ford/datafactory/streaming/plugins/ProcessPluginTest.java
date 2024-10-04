package com.ford.datafactory.streaming.plugins;

import static org.junit.Assert.*;
import org.junit.Test;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessPluginTest {

    @Test
    public void testConvertXmlToJson() throws Exception {
        ProcessPlugin processPlugin = new ProcessPlugin();
        String xmlInput = "<root><element>value</element><nested><item>1</item><item>2</item></nested></root>";
        
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("value", xmlInput);
        
        Map<String, Object> param = new HashMap<>();
        
        List<String> result = processPlugin.convertXmlToJson(messageMap, param);
        
        assertFalse(result.isEmpty());
        String jsonOutput = result.get(0);
        
        // Print the JSON output for debugging
        System.out.println("JSON Output: " + jsonOutput);
        
        // Verify that the output is valid JSON
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(jsonOutput);
        
        // Check the structure and values of the converted JSON
        assertTrue("JSON should have a 'root' element", jsonNode.has("root"));
        assertEquals("value", jsonNode.get("root").get("element").asText());
        assertTrue(jsonNode.get("root").get("nested").has("item"));
        assertEquals(2, jsonNode.get("root").get("nested").get("item").size());
        assertEquals("1", jsonNode.get("root").get("nested").get("item").get(0).asText());
        assertEquals("2", jsonNode.get("root").get("nested").get("item").get(1).asText());
    }

    @Test(expected = Exception.class)
    public void testConvertXmlToJsonWithInvalidXml() throws Exception {
        ProcessPlugin processPlugin = new ProcessPlugin();
        String invalidXml = "<root><unclosed>";
        
        Map<String, Object> messageMap = new HashMap<>();
        messageMap.put("value", invalidXml);
        
        Map<String, Object> param = new HashMap<>();
        
        processPlugin.convertXmlToJson(messageMap, param);
    }
}