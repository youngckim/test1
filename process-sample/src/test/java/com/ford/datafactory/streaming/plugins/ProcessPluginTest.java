package com.ford.datafactory.streaming.plugins;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProcessPluginTest {

    private ProcessPlugin processPlugin;
    private ObjectMapper objectMapper;

    @Before
    public void setUp() {
        processPlugin = new ProcessPlugin();
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testConvertXmlToJson() {
        try {
            String xmlInput = "<root><element>value</element><nested><item>1</item><item>2</item></nested></root>";
            
            Map<String, Object> messageMap = new HashMap<>();
            messageMap.put("value", xmlInput);
            
            Map<String, Object> param = new HashMap<>();
            
            List<String> result = processPlugin.convertXmlToJson(messageMap, param);
            
            assertFalse("Result should not be empty", result.isEmpty());
            String jsonOutput = result.get(0);
            
            System.out.println("XML to JSON Output: " + jsonOutput);
            
            JsonNode jsonNode = objectMapper.readTree(jsonOutput);
            
            assertTrue("JSON should have a 'root' element", jsonNode.has("root"));
            assertEquals("value", jsonNode.get("root").get("element").asText());
            assertTrue(jsonNode.get("root").get("nested").has("item"));
            assertEquals(2, jsonNode.get("root").get("nested").get("item").size());
            assertEquals("1", jsonNode.get("root").get("nested").get("item").get(0).asText());
            assertEquals("2", jsonNode.get("root").get("nested").get("item").get(1).asText());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testProcessSuccessful() {
        try {
            Map<String, Object> messageMap = new HashMap<>();
            String jsonInput = "{\"street\":\"123 Main St\",\"city\":\"Detroit\",\"state\":\"MI\"}";
            messageMap.put("value", jsonInput);
            
            Map<String, Object> param = new HashMap<>();
            
            List<String> result = processPlugin.process(messageMap, param);
            
            assertFalse("Result should not be empty", result.isEmpty());
            String processedMessage = result.get(0);
            
            System.out.println("Processed message: " + processedMessage);
            
            JsonNode jsonNode = objectMapper.readTree(processedMessage);
            
            assertTrue("JSON should have a 'fullAddress' field", jsonNode.has("fullAddress"));
            assertEquals("123 Main St Detroit MI", jsonNode.get("fullAddress").asText());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testProcessWithParam() {
        try {
            Map<String, Object> messageMap = new HashMap<>();
            String jsonInput = "{\"street\":\"123 Main St\",\"city\":\"Detroit\",\"state\":\"MI\"}";
            messageMap.put("value", jsonInput);
            
            Map<String, Object> param = new HashMap<>();
            param.put("env", "PROD");
            
            List<String> result = processPlugin.processWithParam(messageMap, param);
            
            assertFalse("Result should not be empty", result.isEmpty());
            String processedMessage = result.get(0);
            
            System.out.println("Processed message with param: " + processedMessage);
            
            JsonNode jsonNode = objectMapper.readTree(processedMessage);
            
            assertTrue("JSON should have a 'fullAddress' field", jsonNode.has("fullAddress"));
            assertEquals("123 MAIN ST DETROIT MI", jsonNode.get("fullAddress").asText());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test
    public void testProcessWithException() {
        try {
            Map<String, Object> messageMap = new HashMap<>();
            String jsonInput = "{\"street\":\"123 Main St\",\"city\":\"Detroit\",\"state\":\"MI\"}";
            messageMap.put("value", jsonInput);
            
            Map<String, Object> param = new HashMap<>();
            
            List<String> result = processPlugin.processWithException(messageMap, param);
            
            assertFalse("Result should not be empty", result.isEmpty());
            String processedMessage = result.get(0);
            
            System.out.println("Processed message with exception handling: " + processedMessage);
            
            JsonNode jsonNode = objectMapper.readTree(processedMessage);
            
            assertTrue("JSON should have a 'fullAddress' field", jsonNode.has("fullAddress"));
            assertEquals("123 Main St Detroit MI", jsonNode.get("fullAddress").asText());
        } catch (Exception e) {
            e.printStackTrace();
            fail("Exception occurred: " + e.getMessage());
        }
    }

    @Test(expected = Exception.class)
    public void testProcessWithExceptionMissingCity() throws Exception {
        Map<String, Object> messageMap = new HashMap<>();
        String jsonInput = "{\"street\":\"123 Main St\",\"state\":\"MI\"}";
        messageMap.put("value", jsonInput);
        
        Map<String, Object> param = new HashMap<>();
        
        processPlugin.processWithException(messageMap, param);
    }

    @Test(expected = Exception.class)
    public void testThrowException() throws Exception {
        Map<String, Object> messageMap = new HashMap<>();
        Map<String, Object> param = new HashMap<>();
        
        processPlugin.throwException(messageMap, param);
    }
}