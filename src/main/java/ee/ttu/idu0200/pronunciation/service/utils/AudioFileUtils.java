package ee.ttu.idu0200.pronunciation.service.utils;

import java.io.IOException;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class AudioFileUtils {
	public static final String CONTENT_TYPE_AUDIO_MP3 = "audio/mp3";
	public static final String MEDIA_TYPE_AUDIO_MPEG = "audio/mpeg";

	public static boolean isMp3(MultipartFile file) {
		String contentType = file.getContentType();
		Tika tika = new Tika();
		String detectedType;
		
		try {
			detectedType = tika.detect(file.getBytes());
		} catch (IOException e) {
			return false;
		}
		
		return CONTENT_TYPE_AUDIO_MP3.equals(contentType) && MEDIA_TYPE_AUDIO_MPEG.equals(detectedType);
	}
}
