package ee.ttu.idu0200.pronunciation.service.model;

import java.util.Date;

import org.springframework.data.annotation.Id;

public class WordContainer {
	@Id
	private String id;
	private String word;
	private Date lastModified;
	private byte[] pronunciation;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setLastModified(Date lastModified) {
		this.lastModified = lastModified;
	}

	public Date getLastModified() {
		return lastModified;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getWord() {
		return word;
	}

	public void setPronunciation(byte[] pronunciation) {
		this.pronunciation = pronunciation;
	}

	public byte[] getPronunciation() {
		return pronunciation;
	}

	@Override
	public String toString() {
		return "Word[word=" + word + ", modified=" + lastModified.getTime() + "]";
	}
}
