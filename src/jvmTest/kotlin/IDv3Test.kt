import org.farng.mp3.MP3File
import org.jaudiotagger.audio.AudioFileIO
import java.io.File
import kotlin.test.Test

class IDv3Test {
	val audioFileIO = AudioFileIO.getDefaultAudioFileIO()

	val mp3File = File("/home/pushpull/tmp/yelloTest/mp3TestFile.mp3")
	val opusFile = File("/home/pushpull/tmp/yelloTest/opusTestFile.opus")
	val wavFile = File("/home/pushpull/tmp/yelloTest/wavTestFile.wav")

	@Test
	fun mp3File_test() {
		val ext = audioFileIO.readFile(mp3File).audioHeader.format
		println(ext)
		println(ext == "mp3")
	}

	@Test
	fun opusFile_test() {
		val ext = audioFileIO.readFile(opusFile).audioHeader.format
		println(ext == "opus")
	}

	@Test
	fun wavFile_test() {
		val ext = audioFileIO.readFile(wavFile).audioHeader.format
		println(ext)
	}
}