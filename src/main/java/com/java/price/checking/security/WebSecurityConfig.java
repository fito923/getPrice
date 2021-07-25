package com.java.price.checking.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Order(1)
	@Configuration
	class WebSecurityConfigBasic extends WebSecurityConfigurerAdapter {
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			 http
	         .csrf().disable()
	         .authorizeRequests()
	         .antMatchers(HttpMethod.POST, "/test/v1.0.0/getPricesByBrandOrDateOrID").authenticated()
	         .antMatchers(HttpMethod.POST, "/test/v2.0.0/getPricesByBrandOrDateOrID").permitAll()
			  .antMatchers(HttpMethod.POST,"/test/v2.0.0/user").permitAll()			
	         .antMatchers(HttpMethod.GET, "/h2-console/**").permitAll()
			 .antMatchers("/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**","/h2-console/**").permitAll()		         
	         .and()		         
	         .httpBasic();
			
		}
		
		 @Autowired
		    DataSource dataSource;
		    @Autowired
		    public void configureGlobal(AuthenticationManagerBuilder auth) 
		            throws Exception 
		    {

		    	auth.jdbcAuthentication()
		        .dataSource(dataSource)
		        .usersByUsernameQuery("select USER, PASSWORD, true from LOGIN_USER where USER=? and STATE=0")
		        .authoritiesByUsernameQuery("select USER, 'USER' from LOGIN_USER where USER=? and STATE=0");
		    	
		    	
	    	
		    }
		    @Bean 
		    public PasswordEncoder passwordEncoder() { 
		        return NoOpPasswordEncoder.getInstance(); 
		    }
	}

@Order(2)
	@Configuration
	class WebSecurityConfigToken extends WebSecurityConfigurerAdapter {
	
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.csrf().disable()
				.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests()				
				.antMatchers(HttpMethod.POST, "/test/v2.0.0/getPricesByBrandOrDateOrID").authenticated()
				.antMatchers(HttpMethod.POST, "/test/v1.0.0/getPricesByBrandOrDateOrID").permitAll()
				.antMatchers(HttpMethod.POST, "/test/v2.0.0/user").permitAll()				
				.antMatchers("/v2/api-docs",
	                    "/configuration/ui",
	                    "/swagger-resources/**",
	                    "/configuration/security",
	                    "/swagger-ui.html",
	                    "/webjars/**")
				.permitAll()				
				.anyRequest().authenticated();
		}
	}
