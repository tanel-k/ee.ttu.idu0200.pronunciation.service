package ee.ttu.idu0200.pronunciation.service.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ee.ttu.idu0200.pronunciation.service.data_service.WordContainerService;
import ee.ttu.idu0200.pronunciation.service.data_service.objects.InvalidWordException;
import ee.ttu.idu0200.pronunciation.service.data_service.objects.MissingWordContainerException;
import ee.ttu.idu0200.pronunciation.service.data_service.objects.SimplifiedWordContainer;
import ee.ttu.idu0200.pronunciation.service.model.WordContainer;
import ee.ttu.idu0200.pronunciation.service.objects.MissingResourceException;
import ee.ttu.idu0200.pronunciation.service.utils.AudioFileUtils;

@RestController
public class WordContainerController {
	@Autowired
	private WordContainerService wordContainerService;

	@RequestMapping(value = "/words", method = RequestMethod.GET)
	public List<SimplifiedWordContainer> getWords(@RequestParam(name = "query", required = false) String query) {
		if (!StringUtils.isEmpty(query)) {
			return wordContainerService.getByPrefix(query);
		}

		return wordContainerService.getAll();
	}

	@RequestMapping(value = "/words", method = RequestMethod.PUT)
	public ResponseEntity<String> putWord(@RequestPart(name = "word") String word, @RequestPart(name = "pronunciation") MultipartFile pronunciation) {
		if (pronunciation.isEmpty() || !AudioFileUtils.isMp3(pronunciation)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		byte[] pronunciationBytes = null;
		try {
			pronunciationBytes = pronunciation.getBytes();
		} catch (IOException ex) {
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			wordContainerService.createOrUpdate(word, pronunciationBytes);
		} catch (InvalidWordException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@RequestMapping(value = "/words/{wordId}/pronunciation", method = RequestMethod.PUT)
	public ResponseEntity<String> updatePronunciation(@PathVariable String wordId, @RequestParam(name = "pronunciation") MultipartFile pronunciation) {
		if (pronunciation.isEmpty() || !AudioFileUtils.isMp3(pronunciation)) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		byte[] pronunciationBytes = null;
		try {
			pronunciationBytes = pronunciation.getBytes();
		} catch (IOException ex) {
			new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		try {
			wordContainerService.setPronunciation(wordId, pronunciationBytes);
		} catch (MissingWordContainerException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>(HttpStatus.ACCEPTED);
	}

	@ResponseBody
	@RequestMapping(value = "/words/{wordId}/pronunciation", method = RequestMethod.GET, produces = { "audio/mpeg" })
	public byte[] getPronunciation(@PathVariable String wordId) throws MissingResourceException {
		WordContainer word = wordContainerService.getById(wordId);
		if (word == null) {
			throw new MissingResourceException();
		}
		
		return word.getPronunciation();
	}

	@RequestMapping(value = "/words/{wordId}", method = RequestMethod.GET)
	public SimplifiedWordContainer getWord(@PathVariable String wordId) throws MissingResourceException {
		SimplifiedWordContainer wordContainer = wordContainerService.getSimplifiedById(wordId);
		if (wordContainer == null) {
			throw new MissingResourceException();
		}
		
		return wordContainer;
	}

	@ExceptionHandler({ MissingResourceException.class })
	public void handleMissingResourceException(Throwable t, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.NOT_FOUND.value(), t.getMessage());
	}
}
