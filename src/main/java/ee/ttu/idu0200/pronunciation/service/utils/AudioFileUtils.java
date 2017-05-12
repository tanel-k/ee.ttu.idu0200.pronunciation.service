package ee.ttu.idu0200.pronunciation.service.utils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.tika.Tika;
import org.springframework.web.multipart.MultipartFile;

public class AudioFileUtils {
	public static final String CONTENT_TYPE_AUDIO_MP3 = "audio/mp3";
	public static final String MEDIA_TYPE_AUDIO_MPEG = "audio/mpeg";
	public static final String MEDIA_TYPE_APPLICATION_X_MATROSKA= "application/x-matroska";
	public static final Set<String> ACCEPTED_MP3_MEDIA_TYPE_POOL = new HashSet<>();

	static {
		ACCEPTED_MP3_MEDIA_TYPE_POOL.add(MEDIA_TYPE_AUDIO_MPEG);
		ACCEPTED_MP3_MEDIA_TYPE_POOL.add(MEDIA_TYPE_APPLICATION_X_MATROSKA);
	}

	public static boolean isMp3(MultipartFile file) {
		String contentType = file.getContentType();
		Tika tika = new Tika();
		String detectedType;
		
		try {
			detectedType = tika.detect(file.getBytes());
		} catch (IOException e) {
			return false;
		}
		
		return contentType.contains(CONTENT_TYPE_AUDIO_MP3)
			&& ACCEPTED_MP3_MEDIA_TYPE_POOL.contains(detectedType);
	}
}
