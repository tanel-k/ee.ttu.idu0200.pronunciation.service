package ee.ttu.idu0200.pronunciation.service.data_service.objects;

import java.util.Date;

import ee.ttu.idu0200.pronunciation.service.model.WordContainer;

public class SimplifiedWordContainer {
	private String id;
	private String word;
	private Date lastModified;

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

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public static SimplifiedWordContainer forWordContainer(WordContainer word) {
		SimplifiedWordContainer simp = new SimplifiedWordContainer();
		simp.setId(word.getId());
		simp.setWord(word.getWord());
		simp.setLastModified(word.getLastModified());
		return simp;
	}
}
