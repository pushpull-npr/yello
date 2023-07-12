import org.apache.poi.hssf.usermodel.HeaderFooter.file
import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import org.gagravarr.opus.OpusFile
import org.jaudiotagger.audio.AudioFileIO
import java.io.File
import java.io.FileInputStream
import kotlin.test.Test


class IDv3Test {
	val audioFileIO = AudioFileIO.getDefaultAudioFileIO()

	val mp3File = File("/home/pushpull/tmp/yelloTest/mp3TestFile.mp3")
	val opusFile = File("/home/pushpull/Music/The Chainsmokers - Closer (Lyric) ft. Halsey.opus")
//	val opusFile = File("/home/pushpull/tmp/yelloTest/tagmp3_opusTestFile.opus.mp3")
	val wavFile = File("/home/pushpull/tmp/yelloTest/wavTestFile.wav")

	@Test
	fun mp3File_test() {
		val parser = AutoDetectParser()
		val handler = BodyContentHandler()
		val metadata = Metadata()
		val inputstream = FileInputStream(mp3File)
		val context = ParseContext()
		parser.parse(inputstream, handler, metadata, context);

		metadata.get("thumbnail").let {
			println(it)
		}

		metadata.names().forEach {
			println("$it \t ${metadata[it]}")
		}


	}

	@Test
	fun opusFile_test() {
//		val ext = audioFileIO.readFileAs(opusFile, "ogg").audioHeader.format
//		println(ext == "opus")
		OpusFile(opusFile).let {
			println(it.tags.trackNumber)
			println(it.tags.trackNumber)
		}

		val parser = AutoDetectParser()
		val handler = BodyContentHandler()
		val metadata = Metadata()
		val inputstream = FileInputStream(opusFile)
		val context = ParseContext()
		parser.parse(inputstream, handler, metadata, context);


		metadata.names().forEach {
			println("$it \t ${metadata[it]}")
		}
	}

	@Test
	fun wavFile_test() {
		val ext = audioFileIO.readFile(wavFile).audioHeader.format
		println(ext)
	}
}