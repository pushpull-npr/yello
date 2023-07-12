package util

import org.apache.tika.metadata.Metadata
import org.apache.tika.parser.AutoDetectParser
import org.apache.tika.parser.ParseContext
import org.apache.tika.sax.BodyContentHandler
import org.jaudiotagger.audio.AudioFileIO
import java.io.File
import java.io.FileInputStream


object AudioUtil {
	val audioFileIO = AudioFileIO.getDefaultAudioFileIO()

	private val audioMetadataCacheMap: MutableMap<String, AudioMetadata> = mutableMapOf()

	fun getExtension(file: File): String? {
		return try {
			audioFileIO.readFile(file).ext
		} catch (_: Exception) {
			file.extension
		}
	}

	fun getMetadata(file: File): AudioMetadata? {
		if (file.path in audioMetadataCacheMap) {
			return audioMetadataCacheMap[file.path]
		} else {


			val parser = AutoDetectParser()
			val handler = BodyContentHandler()
			val metadata = Metadata()
			val inputStream = FileInputStream(file)
			val context = ParseContext()

			parser.parse(inputStream, handler, metadata, context)

			val genre = metadata.get("xmpDM:genre")
			val album = metadata.get("xmpDM:album")
			val trackNumber = metadata.get("xmpDM:trackNumber")
			val releaseDate = metadata.get("xmpDM:releaseDate")
			val artist = metadata.get("xmpDM:artist")
			val dcCreator = metadata.get("dc:creator")
			val dcTitle = metadata.get("dc:title")
			val albumArtist = metadata.get("xmpDM:albumArtist")
			val duration = metadata.get("xmpDM:duration")
			val audioSampleRate = metadata.get("xmpDM:audioSampleRate")
			val sampleRate = metadata.get("samplerate")
			val channels = metadata.get("channels")
			val discNumber = metadata.get("discnumber")


			val audioMetadata = AudioMetadata(
				genre = genre,
				album = album,
				trackNumber = trackNumber,
				releaseDate = releaseDate,
				artist = artist,
				dcCreator = dcCreator,
				dcTitle = dcTitle,
				albumArtist = albumArtist,
				duration = duration,
				audioSampleRate = audioSampleRate,
				sampleRate = sampleRate,
				channels = channels,
				discNumber = discNumber,
			)

			audioMetadataCacheMap[file.path] = audioMetadata
			return audioMetadata
		}
	}

	fun getMp3FileMetadata(file: File): AudioMetadata? {
		val parser = AutoDetectParser()
		val handler = BodyContentHandler()
		val metadata = Metadata()
		val inputStream = FileInputStream(file)
		val context = ParseContext()

		parser.parse(inputStream, handler, metadata, context)

		val genre = metadata.get("xmpDM:genre")
		val album = metadata.get("xmpDM:album")
		val trackNumber = metadata.get("xmpDM:trackNumber")
		val releaseDate = metadata.get("xmpDM:releaseDate")
		val artist = metadata.get("xmpDM:artist")
		val dcCreator = metadata.get("dc:creator")
		val dcTitle = metadata.get("dc:title")
		val albumArtist = metadata.get("xmpDM:albumArtist")
		val duration = metadata.get("xmpDM:duration")
		val audioSampleRate = metadata.get("xmpDM:audioSampleRate")
		val sampleRate = metadata.get("samplerate")
		val channels = metadata.get("channels")
		val discNumber = metadata.get("discnumber")

		return AudioMetadata(
			genre = genre,
			album = album,
			trackNumber = trackNumber,
			releaseDate = releaseDate,
			artist = artist,
			dcCreator = dcCreator,
			dcTitle = dcTitle,
			albumArtist = albumArtist,
			duration = duration,
			audioSampleRate = audioSampleRate,
			sampleRate = sampleRate,
			channels = channels,
			discNumber = discNumber,
		)
	}

	fun getWavFileMetadata(): AudioMetadata? {
		return null
	}

	fun getOpusFileMetadata(): AudioMetadata? {
		return null
	}

}

data class AudioMetadata(
	val genre: String?,
	val album: String?,
	val trackNumber: String?,
	val releaseDate: String?,
	val artist: String?,
	val dcCreator: String?,
	val dcTitle: String?,
	val albumArtist: String?,
	val duration: String?,
	val audioSampleRate: String?,
	val sampleRate: String?,
	val channels: String?,
	val discNumber: String?,
)
