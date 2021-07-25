package com.java.test.price.checking.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.java.price.checking.PriceCheckingApplication;
import com.java.price.checking.repositories.PricesRepositoryImpl;
import com.java.price.checking.security.UserController;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.is;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { PriceCheckingApplication.class })
@SpringBootTest
@AutoConfigureMockMvc

class PriceCheckingApplicationTests {

	
	@Autowired
	 private PricesRepositoryImpl repo;
	
	@Test
	@DisplayName("@DisplayName consulta por marca  para obtener todos los resultados")
	void test_Get_All_by_Brand() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("marca", 1);
		assertEquals(4, repo.getData(datosTest).size());
	}
	
	@Test
	@DisplayName("@DisplayName consulta por marca para no obtener resultados")
	void test_Get_No_One_by_Brand() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("marca", 2);
		assertEquals(0, repo.getData(datosTest).size());
	}
	
	
	
	@Test
	@DisplayName("@DisplayName consulta por id para obtener todos los resultados")
	void test_Get_All_by_Id() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("id", "35455");
		assertEquals(4, repo.getData(datosTest).size());
	}
	
	
	@Test
	@DisplayName("@DisplayName consulta por id para no obtener resultados")
	void test_Get_No_One_by_Id() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("id", "35255");
		assertEquals(0, repo.getData(datosTest).size());
	}
	

	
	@Test
	@DisplayName("@DisplayName consulta por fecha para  obtener 2 resultados")
	void test_Get_Two_rows_By_Date() {
		final Date fecha =new GregorianCalendar(2020, 6, 15, 10, 55).getTime();
		
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("fecha", fecha);
		assertEquals(2, repo.getData(datosTest).size());
	}
	
	@Test
	@DisplayName("@DisplayName consulta por fecha para no obtener resultados")
	void test_Get_No_One_By_Date() {
		final Date fecha =new GregorianCalendar(2019, 6, 15, 10, 55).getTime();
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		datosTest.put("fecha", fecha);
		assertEquals(0, repo.getData(datosTest).size());
	}
	
	@Test
	@DisplayName("@DisplayName consulta por id, fecha para obtener 2 resultado")
	void test_Get_Two_rows_by_Id_And_Date() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		
		final Date fecha =new GregorianCalendar(2020, 6, 14, 16, 55).getTime();
		datosTest.put("id", "35455");
		datosTest.put("fecha", fecha);
		assertEquals(2, repo.getData(datosTest).size());
	}
	
	@Test
	@DisplayName("@DisplayName consulta por id, fecha y maraca para obtner un resultado")
	void test_GetTwobyIdAndDateAndBrand() {
		HashMap<String, Object>  datosTest= new 	HashMap<String, Object>();
		
		final Date fecha =new GregorianCalendar(2020, 6, 14, 16, 55).getTime();
		datosTest.put("id", "35455");
		datosTest.put("fecha", fecha);
		datosTest.put("marca", 1);
		assertEquals(1, repo.getData(datosTest).size());
	}
	@Autowired
	private MockMvc mockMvc;


	
	@Test
	@DisplayName("@DisplayName Valida la seguridad")
	void test_Endpoint_validate_security() throws Exception {		
		
		final String peticion="{\n"
		+ "\"marca\":1,\n"
		+ "\"id\":\"35455\",\n"
		+ "\"fecha\":\"2020-06-24 11:00:01\"\n"
		+ "}";
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/getPricesByBrandOrDateOrID")
			.content(peticion)			
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().is(404))
            .andReturn();
    
	}
	
	//----------------------------------------- Endpoint v1.0
		@Test
		@DisplayName("@DisplayName api V1 Consulta a través del endpoing para obtener la tarifa 4")
		void test_Endpoint_Get_price_list_4_V1() throws Exception {		
			
			final String peticion="{\n"
			+ "\"marca\":1,\n"
			+ "\"id\":\"35455\",\n"
			+ "\"fecha\":\"2020-06-24 11:00:01\"\n"
			+ "}";
			
			
			String token=UserController.getJWTToken("test_Endpoint_Get_price_list_4");
			
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)	
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk())
	    		.andExpect(jsonPath("$['data'].[0].tarifa", is(4)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
		
			
		
		@Test
		@DisplayName("@DisplayName api V1 Consulta a través del endpoing con un format de fecha incorrecto")
		void test_Endpoint_Error_Wrong_Format_Date_V1() throws Exception {
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-156-4411:00:01\"\n"
					+ "}";
			String token=UserController.getJWTToken("test_Endpoint_Error_Wrong_Format_Date");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")			
				.content(peticion).header("Authorization", token)
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().is4xxClientError()).andExpect(jsonPath("$['errorMessage']", is("Incorrect format Date, correct is yyyy-MM-dd HH:mm:ss")))
	    		.andReturn();
	    
		}
		
		@Test
		@DisplayName("@DisplayName api V1  Consulta a través del endpoing para marca 2, sin resultados")
		void test_Endpoint_NotResults_V1() throws Exception {
			final String peticion="{\n"
					+ "\"marca\":2,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-24 11:00:01\"\n"
					+ "}";
			
			String token=UserController.getJWTToken("test_Endpoint_NotResults");
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(jsonPath("$['data'].length()", is(0)))
	    		.andReturn();
	    
		}
		
		@Test
		@DisplayName("@DisplayName Test 1, api V1: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
		void test_1_V1() throws Exception {		
			
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-14 10:00:01\"\n"
					+ "}";
			
			String token=UserController.getJWTToken("test_1");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(1)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
		@Test
		@DisplayName("@DisplayName Test 2 api V1: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
		void tes_2_V1() throws Exception {		
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-14 16:00:01\"\n"
					+ "}";
			String token=UserController.getJWTToken("test_2");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(2)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
		
		@Test
		@DisplayName("@DisplayName  Test 3 api V1: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
		void tes_3_V1() throws Exception {		
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-14 21:00:01\"\n"
					+ "}";
			
			String token=UserController.getJWTToken("test_3");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(1)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
		@Test
		@DisplayName("@DisplayName Test 4 api V1: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)")
		void tes_4_V1() throws Exception {		
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-15 10:00:01\"\n"
					+ "}";
			
			String token=UserController.getJWTToken("test_4");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)				
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(3)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
		
		@Test
		@DisplayName("@DisplayName Test 5 api V1: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)")
		void tes_5_V1() throws Exception {		
			
			final String peticion="{\n"
					+ "\"marca\":1,\n"
					+ "\"id\":\"35455\",\n"
					+ "\"fecha\":\"2020-06-16 21:00:01\"\n"
					+ "}";
			
			String token=UserController.getJWTToken("tes_5");
			
			mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
				.content(peticion).header("Authorization", token)	
	            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
	    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(4)))
	    		.andExpect(jsonPath("$['data'].length()", is(1)))
	            .andReturn();
	    
		}
	
	
	//----------------------------------------- Endpoint v2.0
	@Test
	@DisplayName("@DisplayName api V2 Consulta a través del endpoing para obtener la tarifa 4")
	void test_Endpoint_Get_price_list_4_V2() throws Exception {		
		
		final String peticion="{\n"
		+ "\"marca\":1,\n"
		+ "\"id\":\"35455\",\n"
		+ "\"fecha\":\"2020-06-24 11:00:01\"\n"
		+ "}";
		
		
		String token=UserController.getJWTToken("test_Endpoint_Get_price_list_4");
		
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)	
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk())
    		.andExpect(jsonPath("$['data'].[0].tarifa", is(4)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	
		
	
	@Test
	@DisplayName("@DisplayName api V2 Consulta a través del endpoing con un format de fecha incorrecto")
	void test_Endpoint_Error_Wrong_Format_Date_V2() throws Exception {
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-156-4411:00:01\"\n"
				+ "}";
		String token=UserController.getJWTToken("test_Endpoint_Error_Wrong_Format_Date");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")			
			.content(peticion).header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().is4xxClientError()).andExpect(jsonPath("$['errorMessage']", is("Incorrect format Date, correct is yyyy-MM-dd HH:mm:ss")))
    		.andReturn();
    
	}
	
	@Test
	@DisplayName("@DisplayName api V2 Consulta a través del endpoing para marca 2, sin resultados")
	void test_Endpoint_NotResult_V2s() throws Exception {
		final String peticion="{\n"
				+ "\"marca\":2,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-24 11:00:01\"\n"
				+ "}";
		
		String token=UserController.getJWTToken("test_Endpoint_NotResults");
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(jsonPath("$['data'].length()", is(0)))
    		.andReturn();
    
	}
	
	@Test
	@DisplayName("@DisplayName Test 1   api V2: petición a las 10:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
	void test_1_V2() throws Exception {		
		
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-14 10:00:01\"\n"
				+ "}";
		
		String token=UserController.getJWTToken("test_1");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(1)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	@Test
	@DisplayName("@DisplayName Test 2  api V2: petición a las 16:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
	void tes_2_V2() throws Exception {		
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-14 16:00:01\"\n"
				+ "}";
		String token=UserController.getJWTToken("test_2");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(2)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	
	@Test
	@DisplayName("@DisplayName Test 3  api V2: petición a las 21:00 del día 14 del producto 35455   para la brand 1 (ZARA)")
	void tes_3_V2() throws Exception {		
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-14 21:00:01\"\n"
				+ "}";
		
		String token=UserController.getJWTToken("test_3");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(1)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	@Test
	@DisplayName("@DisplayName Test 4  api V2: petición a las 10:00 del día 15 del producto 35455   para la brand 1 (ZARA)")
	void tes_4_V2() throws Exception {		
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-15 10:00:01\"\n"
				+ "}";
		
		String token=UserController.getJWTToken("test_4");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)				
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(3)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	
	@Test
	@DisplayName("@DisplayName Test 5  api V2: petición a las 21:00 del día 16 del producto 35455   para la brand 1 (ZARA)")
	void tes_5_V2() throws Exception {		
		
		final String peticion="{\n"
				+ "\"marca\":1,\n"
				+ "\"id\":\"35455\",\n"
				+ "\"fecha\":\"2020-06-16 21:00:01\"\n"
				+ "}";
		
		String token=UserController.getJWTToken("tes_5");
		
		mockMvc.perform(MockMvcRequestBuilders.post("/test/v2.0.0/getPricesByBrandOrDateOrID")
			.content(peticion).header("Authorization", token)	
            .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
    		.andExpect(status().isOk()).andExpect(jsonPath("$['data'].[0].tarifa", is(4)))
    		.andExpect(jsonPath("$['data'].length()", is(1)))
            .andReturn();
    
	}
	
		
}
