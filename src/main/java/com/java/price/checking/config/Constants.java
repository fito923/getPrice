package com.java.price.checking.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "constantes.campos")
public class Constants {
	
	private  String marca;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
	private  String id;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
	private  String fecha;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
	
	private  String idProducto;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
	private  String fechaInicio;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
	private  String fechaFin;// definidas constantes, por si se usaran en más sitios. De momento solo se usan en un sitio
}


	
