package com.java.price.checking.entities;



import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.DateDeserializer;

import lombok.Data;

@Entity 
@Data
@Table(name = "PRICES") 

public class PricesEntity {
	
	
	@JsonIgnore
    @ManyToOne
    @JoinColumn(name = "BRAND_ID")
    CompaniesEntity marca;
    
    @Column(name="START_DATE")
    @JsonProperty("fecha_inicio_aplicacion")
    @JsonFormat(locale = "es", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Madrid")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)    
    Date fechaInicio;
    
    @Column(name="END_DATE")
    @JsonProperty("fecha_fin_aplicacion")
	@Temporal(TemporalType.TIMESTAMP)
    @JsonDeserialize(using = DateDeserializer.class)
    @JsonFormat(locale = "es", shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = "Europe/Madrid")
    @DateTimeFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    Date fechaFin;
    
    @JsonIgnore
    @EmbeddedId
     PricesId id;
    	
    @JsonIgnore
	@Column(name="PRIORITY")
	 int prioridad;
	
	
	@Column(name="PRICE")
	 BigDecimal precio;
	
    @JsonProperty("Moneda")
	@Column(name="CURR")
	 String moneda;

	@Column(name ="PRODUCT_ID", insertable = false ,updatable = false)
	@JsonProperty("id_producto")
	 String idProducto;
	
	@Column(name ="PRICE_LIST", insertable = false ,updatable = false)
	@JsonProperty("tarifa")
	 int tarifa;
	
	@Column(name ="BRAND_ID", insertable = false ,updatable = false)
	@JsonProperty("id_cadena")
	 int idCadena;
    

}
