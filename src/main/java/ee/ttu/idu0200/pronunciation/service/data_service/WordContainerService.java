package ee.ttu.idu0200.pronunciation.service.data_service;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ee.ttu.idu0200.pronunciation.service.data_service.objects.InvalidWordException;
import ee.ttu.idu0200.pronunciation.service.data_service.objects.MissingWordContainerException;
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
		return wordContainerRepository.findByWordStartsWithOrderByWordAsc(prefix).stream()
				.map(SimplifiedWordContainer::forWordContainer).collect(Collectors.toList());
	}

	public List<SimplifiedWordContainer> getAll() {
		return wordContainerRepository.findAll().stream()
				.map(SimplifiedWordContainer::forWordContainer).collect(Collectors.toList());
	}

	public WordContainer getById(String id) {
		return wordContainerRepository.findOne(id);
	}

	public SimplifiedWordContainer getSimplifiedById(String id) {
		WordContainer wordContainer = getById(id);
		if (wordContainer != null) {
			return SimplifiedWordContainer.forWordContainer(wordContainer);
		}
		
		return null;
	}

	public void createOrUpdate(String word, byte[] pronunciation) throws InvalidWordException {
		word = normalizeWord(word);
		Date now = new Date();
		
		if (doesContainerExist(word)) {
			WordContainer wordContainer = wordContainerRepository.findByWord(word);
			wordContainer.setPronunciation(pronunciation);
			wordContainer.setLastModified(now);
			wordContainerRepository.save(wordContainer);
			return;
		}
		
		if (!VALID_WORD_PATTERN.matcher(word).matches()) {
			throw new InvalidWordException();
		}
		
		WordContainer wordContainer = new WordContainer();
		wordContainer.setWord(word);
		wordContainer.setLastModified(now);
		wordContainer.setPronunciation(pronunciation);
		wordContainerRepository.save(wordContainer);
	}

	public void setPronunciation(String id, byte[] pronunciation) throws MissingWordContainerException {
		WordContainer wordContainer = wordContainerRepository.findOne(id);
		if (wordContainer == null) {
			throw new MissingWordContainerException();
		}
		
		wordContainer.setPronunciation(pronunciation);
		wordContainer.setLastModified(new Date());
		wordContainerRepository.save(wordContainer);
	}

	public boolean doesContainerExist(String word) {
		return wordContainerRepository.findByWord(word) != null;
	}

	public static String normalizeWord(String word) {
		return word.trim().toLowerCase();
	}

}
