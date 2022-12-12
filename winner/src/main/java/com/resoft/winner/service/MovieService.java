package com.resoft.winner.service;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.resoft.winner.entity.Movie;
import com.resoft.winner.entity.Producer;
import com.resoft.winner.repository.ProducerRepository;
import com.resoft.winner.response.MovieListResponse;
import com.resoft.winner.response.MovieResponse;

@Service
public class MovieService {
	
	
	@Autowired
	private ProducerRepository producerRepository;
	
	private static Logger logger = LoggerFactory.getLogger(MovieService.class);
	
	public MovieListResponse findWinnerMovie() {
		logger.info("Recenbendo uma chamada para busca dos filmes.");		
		TreeMap<Integer, List<MovieResponse>> mapFilmeDTO = new TreeMap<Integer, List<MovieResponse>>();		
		
		logger.info("Iniciando busca dos filmes no banco de dados");
		List<Producer> listProducer = producerRepository.findAll();
		logger.info("Finalizou a busca dos filmes no banco de dados");
		logger.info("Iniciando a verificacao do intervalo dos filmes vencedores");
		for(Producer producer : listProducer) {
			for (int i = 1; i < producer.getMovies().size(); i++) {				
				Movie moviePrevious = producer.getMovies().get(i - 1);
				Movie movieFollowing = producer.getMovies().get(i);
				Integer interval = movieFollowing.getYear() - moviePrevious.getYear();
				MovieResponse filmeDTO = new MovieResponse(producer.getName(), interval, moviePrevious.getYear(), movieFollowing.getYear());								
				
				if(mapFilmeDTO.get(interval) != null) {
					mapFilmeDTO.get(interval).add(filmeDTO);
				}else {
					List<MovieResponse> lFilmeDTO = new ArrayList<>();
					lFilmeDTO.add(filmeDTO);
					mapFilmeDTO.put(interval, lFilmeDTO);
				}
			}					
		}
		MovieListResponse movieListResponse = new MovieListResponse();
		if(!mapFilmeDTO.isEmpty()) {
			movieListResponse.getMin().addAll(mapFilmeDTO.get(mapFilmeDTO.firstKey()));		
			movieListResponse.getMax().addAll(mapFilmeDTO.get(mapFilmeDTO.lastKey()));						
		}
		logger.info("Finalizou a consulta dos intervalos, retornando objeto para o request");
		return movieListResponse;
	}


}
