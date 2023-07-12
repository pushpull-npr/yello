package singleton

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import uk.co.caprica.vlcj.media.*
import uk.co.caprica.vlcj.player.base.State
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import java.io.File


object FakeAudioPlayer {
	private val audioPlayer = AudioPlayerComponent()

	var isParserAvailable2 = true
	fun isParserAvailable(): Boolean = isParserAvailable2

	val albumArtMap: MutableMap<String, AlbumArt> = mutableMapOf()

	val parseCoroutine = CoroutineScope(Dispatchers.IO)

	val fileFlow = MutableStateFlow<String?>(null)

	init {

		audioPlayer.mediaPlayer().events().addMediaEventListener(object : MediaEventListener {
			override fun mediaMetaChanged(media: Media?, metaType: Meta?) {
			}

			override fun mediaSubItemAdded(media: Media?, newChild: MediaRef?) {
			}

			override fun mediaDurationChanged(media: Media?, newDuration: Long) {
			}

			override fun mediaParsedChanged(media: Media?, newStatus: MediaParsedStatus?) {
			}

			override fun mediaFreed(media: Media?, mediaFreed: MediaRef?) {
			}

			override fun mediaStateChanged(media: Media?, newState: State?) {
			}

			override fun mediaSubItemTreeAdded(media: Media?, item: MediaRef?) {
			}

			override fun mediaThumbnailGenerated(media: Media?, picture: Picture?) {
				try {
					picture?.buffer()?.size?.let {
						println(it)
					}
				} catch (e: Exception) {
					e.printStackTrace()
				}
			}
		}
		)

		CoroutineScope(Dispatchers.IO).launch {
			launch {
				fileFlow.collect { filePath ->
					if (filePath != null) {
						try {
							println(filePath)
							while (!isParserAvailable()) {
								delay(10)
								println("waiting : $filePath")
							}
							println("npr")
							isParserAvailable2 = false
							println("parser blocked")

							val media = audioPlayer.mediaPlayer().media()
							media.prepare(filePath)
							val parser = audioPlayer.mediaPlayer().media().parsing()
							parser.parse()

							while (media.parsing().status() == null) {
								println(media.parsing().status())
								delay(10)
								if (media.parsing().status() == MediaParsedStatus.DONE) break
							}
							println("parser unblocked")
							isParserAvailable2 = true
						} catch (e: Exception) {
							isParserAvailable2 = true
						}
					}
				}
			}
		}

		fileFlow.tryEmit(null)
		fileFlow.tryEmit("/home/pushpull/Music/Breakout/t/Full Circle.mp3")
		fileFlow.tryEmit("/home/pushpull/Music/Breakout/t/7 Things - Single Version.mp3")
	}

	fun getArtWork(file: File) {
//		if (file.path !in albumArtMap)
////		println("getArtWork : ${file.path}")
//			fileFlow.tryEmit(file.path).let {
//				println(it)
//			}
	}
}

data class AlbumArt(val path: String?)
