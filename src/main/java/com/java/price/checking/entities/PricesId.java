package com.java.price.checking.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import com.fasterxml.jackson.annotation.JsonProperty;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Embeddable
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor 

public class PricesId implements Serializable{


    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@JsonProperty("id")
	@Column(name="PRODUCT_ID")
	private String idProducto;
    
	@JsonProperty("tarifa")
    @Column(name="PRICE_LIST")
    private int listaPrecio;
	
}
