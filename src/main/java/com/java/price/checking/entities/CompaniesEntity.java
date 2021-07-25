package com.java.price.checking.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Entity 
@Data
@Table(name = "GROUP_COMPANY", uniqueConstraints = { @UniqueConstraint(columnNames = {"ID"})}) 


public class CompaniesEntity {
	
	@Id
	private int ID;
	
	@JsonProperty("nombre_cadena")
	@Column(name="NAME")
	private String name;

}
