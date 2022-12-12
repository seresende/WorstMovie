package com.resoft.winner;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.resoft.winner.entity.Movie;
import com.resoft.winner.entity.Producer;
import com.resoft.winner.repository.MovieRepository;
import com.resoft.winner.repository.ProducerRepository;
import com.resoft.winner.util.ArqCSV;
import com.resoft.winner.util.MovieValidate;

@SpringBootApplication
@EnableJpaRepositories
public class WinnerApplication implements CommandLineRunner{

	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private ProducerRepository producerRepository;
	
	@Value( "${csv.name.file}" )
	private String nameCSV;
	
	private static Logger logger = LoggerFactory.getLogger(WinnerApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(WinnerApplication.class, args);
	}
	
    public void run(String... args) throws Exception {
		logger.info("Iniciando leitura de arquivo.");
		ArqCSV csvService = new ArqCSV();		
		List<Movie> listMovies = csvService.readCSVtoList(nameCSV);
		MovieValidate movieValidate = new MovieValidate();
		movieValidate.validMovie(listMovies);
		logger.info("Finalizando a leitura do arquivo.");
		logger.info("Iniciando persistencia no banco.");
		for (Movie movie : listMovies) {
			if(movie != null && movie.getProducers() != null && !movie.getProducers().isEmpty()) {
				for(Producer producer : movie.getProducers()) {
					Long producerID =  producerRepository.findProducerByName(producer.getName());
					if(producerID != null) {
						producer.setId(producerID);
					}else {
						producerRepository.save(producer);					
					}				
				}
				movieRepository.save(movie);
			}						
		}
		logger.info("Finalizado");	
		
	}	

}
