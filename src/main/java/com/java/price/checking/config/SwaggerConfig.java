package com.java.price.checking.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    public SwaggerConfig() {
		super();
	}

	@Bean
	public Docket apiDocket100() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Test Java API-1.0.0")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.java.price"))
				 .paths(regex("/test/v1.0.0.*"))	
				.build()
				.apiInfo(new ApiInfoBuilder().version("1.0.0").title("Test Java API").description("Documentation Test Java API v1.0.0").build())
				.useDefaultResponseMessages(false)
				;
	}
	
	@Bean
	public Docket apiDocket200() {
		return new Docket(DocumentationType.SWAGGER_2).groupName("Test Java API-2.0.0")
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.java.price"))
				 .paths(regex("/test/v2.0.0.*"))	
				.build()
				.apiInfo(new ApiInfoBuilder().version("2.0.0").title("Test Java API").description("Documentation Test Java API v2.0.0").build())
				.useDefaultResponseMessages(false)
				;
	}
	
	
	
	
	   private Predicate <String> userPaths() {
	       return regex("/**");
	   }

}
