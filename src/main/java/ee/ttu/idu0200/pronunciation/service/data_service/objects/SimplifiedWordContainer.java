package ee.ttu.idu0200.pronunciation.service.data_service.objects;

import ee.ttu.idu0200.pronunciation.service.model.WordContainer;

public class SimplifiedWordContainer {
	private String id;
	private String word;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public static SimplifiedWordContainer forWord(WordContainer word) {
		SimplifiedWordContainer simp = new SimplifiedWordContainer();
		simp.setId(word.getId());
		simp.setWord(word.getWord());
		return simp;
	}
}
