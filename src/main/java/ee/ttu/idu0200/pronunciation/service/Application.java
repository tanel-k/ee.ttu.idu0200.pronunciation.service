package ee.ttu.idu0200.pronunciation.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/*
	 * implements CommandLineRunner:
	 * 
	 * @Autowired
	 * private WordContainerRepository wordRepository;
	 * @Override
	 * public void run(String... args) throws Exception {
	 *  wordRepository.deleteAll();
	 * }
	 */
}
