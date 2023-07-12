package singleton

import org.jaudiotagger.audio.AudioFileIO
import java.io.File


object AudioUtil {
	val audioFileIO = AudioFileIO.getDefaultAudioFileIO()

	fun getExtension(file : File) : String? {
		return try {
			audioFileIO.readFile(file).ext
		} catch (_ : Exception) {
			file.extension
		}
	}
}