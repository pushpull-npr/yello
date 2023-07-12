package konstant


enum class AudioMimeType {
	MP3,
	OPUS,
	UNKNOWN
}

val mimeTypeMap : Map<String, AudioMimeType> = mapOf(
	"audio/mpeg" to AudioMimeType.MP3,
	"audio/opus" to AudioMimeType.OPUS,
)
