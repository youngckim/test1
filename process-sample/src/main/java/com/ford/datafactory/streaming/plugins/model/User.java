package com.ford.datafactory.streaming.plugins.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
	
	private static final long serialVersionUID = 5125708170376620193L;
	
	private String id;
    private String name;
    private String age;
    private String street;
    private String city;
    private String state;
    private String fullAddress;
    private String type;

}