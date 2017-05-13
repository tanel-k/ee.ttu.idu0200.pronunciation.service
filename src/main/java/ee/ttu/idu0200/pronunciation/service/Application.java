package ee.ttu.idu0200.pronunciation.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
public class Application implements CommandLineRunner {
	/*
	@Autowired
	private WordContainerRepository wordRepository;
	*/

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		wordRepository.deleteAll();
		
		WordContainer newContainer = new WordContainer();
		newContainer.setWord("aabits");
		newContainer.setLastModified(new Date());
		wordRepository.save(newContainer);
		
		newContainer = new WordContainer();
		newContainer.setWord("ahven");
		newContainer.setLastModified(new Date());
		wordRepository.save(newContainer);
		*/
	}

	@Bean
	public CorsFilter corsFilter() {
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.addAllowedOrigin("*");
		config.addAllowedHeader("*");
		config.addAllowedMethod("GET");
		config.addAllowedMethod("POST");
		config.addAllowedMethod("PUT");
		config.addAllowedMethod("DELETE");
		config.addAllowedMethod("OPTIONS");
		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}
}
