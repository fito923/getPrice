package com.java.price.checking.response;

import java.sql.Timestamp;
import java.util.List;

import com.java.price.checking.entities.PricesEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor 
public class Response {
	//Atributes
	private Timestamp timestamp;
	private  List<PricesEntity> data;
	private int respondeCode;
	private String errorMessage;
	private String status;
}
