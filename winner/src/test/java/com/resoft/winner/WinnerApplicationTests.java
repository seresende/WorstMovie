package com.resoft.winner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.resoft.winner.entity.Movie;
import com.resoft.winner.entity.Producer;
import com.resoft.winner.repository.MovieRepository;
import com.resoft.winner.repository.ProducerRepository;
import com.resoft.winner.response.MovieListResponse;
import com.resoft.winner.response.MovieResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class WinnerApplicationTests {

	@Autowired
	private MockMvc mvc;
	
	@Autowired
	private MovieRepository filmeRepository;
	
	@Autowired
	private ProducerRepository producerRepository;
	
	@Autowired
	private ObjectMapper objectMapper;


	@Test
	void testServiceOk() throws Exception {
		mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
	}
	
	@Test
	void testExistsMinMax() throws Exception {
		MvcResult result = mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
	            .andReturn();

		String content = result.getResponse().getContentAsString();
		MovieListResponse responseListFilme = objectMapper.readValue(content, MovieListResponse.class);
		assertTrue(responseListFilme != null && responseListFilme.getMax() != null && !responseListFilme.getMax().isEmpty()
				&& responseListFilme.getMin()!= null && !responseListFilme.getMin().isEmpty()); 		
				
	}
	
	
	@Test
	void testNewMaxInterval() throws Exception {
		MvcResult result = mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
	            .andReturn();

		String content = result.getResponse().getContentAsString();
		MovieListResponse responseListFilme = objectMapper.readValue(content, MovieListResponse.class);
		String nameNewMaxProducer = "TestNewMaxInterval";
		
		Producer producer = producerRepository.save(new Producer(nameNewMaxProducer));
		
		int interval = 9999;
		if(responseListFilme != null && responseListFilme.getMax() != null && !responseListFilme.getMax().isEmpty()) {
			interval = responseListFilme.getMax().get(0).getInterval() + 1;
		}
		
		
		filmeRepository.save(new Movie(2000, "Filme Teste Max", "Studios Teste", Arrays.asList(producer), true));
		filmeRepository.save(new Movie(interval, "Filme Teste Max 2", "Studios Teste", Arrays.asList(producer), true));
		
		MvcResult result2 = mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
	            .andReturn();

		MovieListResponse responseListFilme2 = objectMapper.readValue(result2.getResponse().getContentAsString(), MovieListResponse.class);
		if(responseListFilme != null && responseListFilme.getMax() != null && !responseListFilme.getMax().isEmpty()) {
			assertNotEquals(responseListFilme.getMax().get(0).getProducer(), responseListFilme2.getMax().get(0).getProducer());
		}		
		assertEquals(responseListFilme2.getMax().get(0).getProducer(), nameNewMaxProducer);
		
	}
	
	@Test
	void testAddMinInterval() throws Exception {
		MvcResult result = mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
	            .andReturn();

		String content = result.getResponse().getContentAsString();
		MovieListResponse responseListFilme = objectMapper.readValue(content, MovieListResponse.class);
		String nameNewMinProducer = "TestNewMinInterval";		
		Producer producer = producerRepository.save(new Producer(nameNewMinProducer));	
		
		int interval = 1;
		if(responseListFilme != null && responseListFilme.getMin() != null && !responseListFilme.getMin().isEmpty()) {
			interval =  responseListFilme.getMin().get(0).getInterval();
		}
		
		filmeRepository.save(new Movie(2000, "Filme Teste Min", "Studios Teste", Arrays.asList(producer), true));
		filmeRepository.save(new Movie(2000 + interval, "Filme Teste Min 2", "Studios Teste", Arrays.asList(producer), true));
		MvcResult result2 = mvc.perform(get("/movies").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())				
	            .andReturn();

		MovieListResponse responseListFilme2 = objectMapper.readValue(result2.getResponse().getContentAsString(), MovieListResponse.class);
		assertTrue(responseListFilme2 != null && responseListFilme2.getMin() != null && !responseListFilme2.getMin().isEmpty());
		
		if(responseListFilme != null && responseListFilme.getMin() != null && !responseListFilme.getMin().isEmpty()) {
			assertTrue(responseListFilme.getMin().size() < responseListFilme2.getMin().size());
			boolean producerFound = false;
			for(MovieResponse movieResponse : responseListFilme2.getMin()) {
				if(movieResponse.getProducer().equalsIgnoreCase(nameNewMinProducer)) {
					producerFound = true; 
					break;
				}
			}
			assertTrue(producerFound);
		}		
				
	}

}
