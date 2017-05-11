package ee.ttu.idu0200.pronunciation.service.data_service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.idu0200.pronunciation.service.data_service.objects.SimplifiedWordContainer;
import ee.ttu.idu0200.pronunciation.service.model.WordContainer;
import ee.ttu.idu0200.pronunciation.service.repository.WordContainerRepository;

@Service
public class WordContainerService {
	private static final Pattern VALID_WORD_PATTERN = Pattern.compile("[a-z]+");

	@Autowired
	private WordContainerRepository wordContainerRepository;

	public List<SimplifiedWordContainer> getByPrefix(String prefix) {
		if (prefix == null || prefix.length() < 1) {
			return new LinkedList<>();
		}
		
		prefix = normalizeWord(prefix);
		return wordContainerRepository.findByWordStartsWith(prefix).stream()
				.map(SimplifiedWordContainer::forWord).collect(Collectors.toList());
	}

	public List<SimplifiedWordContainer> getAll() {
		return wordContainerRepository.findAll().stream()
				.map(SimplifiedWordContainer::forWord).collect(Collectors.toList());
	}

	public WordContainer getById(String id) {
		return wordContainerRepository.findOne(id);
	}

	public void createOrUpdate(String word, byte[] pronunciation) throws InvalidWordException {
		word = normalizeWord(word);
		
		if (doesContainerExist(word)) {
			WordContainer wordContainer = wordContainerRepository.findByWord(word);
			wordContainer.setPronunciation(pronunciation);
			wordContainerRepository.save(wordContainer);
			return;
		}
		
		if (!VALID_WORD_PATTERN.matcher(word).matches()) {
			throw new InvalidWordException();
		}
		
		WordContainer wordContainer = new WordContainer();
		wordContainer.setWord(word);
		wordContainer.setPronunciation(pronunciation);
		wordContainerRepository.save(wordContainer);
	}

	public void setPronunciation(String id, byte[] pronunciation) throws MissingWordContainerException {
		WordContainer wordContainer = wordContainerRepository.findOne(id);
		if (wordContainer == null) {
			throw new MissingWordContainerException();
		}
		
		wordContainer.setPronunciation(pronunciation);
		wordContainerRepository.save(wordContainer);
	}

	public boolean doesContainerExist(String word) {
		return wordContainerRepository.findByWord(word) != null;
	}

	public static String normalizeWord(String word) {
		return word.trim().toLowerCase();
	}

}
