package com.java.price.checking.request;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonInclude(JsonInclude.Include.NON_NULL)


@Data
@AllArgsConstructor
@NoArgsConstructor 
public class Request implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4202568256848169527L;
	@JsonProperty("marca")
	@ApiModelProperty(value = "marca", name = "marca", dataType = "Int" ,example = "1")
	Integer marca;
	
	@JsonProperty("id")
	@ApiModelProperty(value = "id", name = "id", dataType = "String" ,example = "35455")
	String id;			 
	
	@JsonProperty("fecha")
	@JsonFormat(locale = "es", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Madrid")
	@ApiModelProperty(value = "fecha", name = "fecha", dataType = "Date" ,example = "2020-06-15 22:00:01")
	 Date fecha;
	
	

}

