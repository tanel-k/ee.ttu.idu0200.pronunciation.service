package ee.ttu.idu0200.pronunciation.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ee.ttu.idu0200.pronunciation.service.model.WordContainer;

public interface WordContainerRepository extends MongoRepository<WordContainer, String> {
	public List<WordContainer> findByWordStartsWith(String prefix);
	public WordContainer findByWord(String word);
}
