package presentation.main

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import uk.co.caprica.vlcj.binding.support.strings.NativeString
import uk.co.caprica.vlcj.binding.support.strings.NativeUri
import uk.co.caprica.vlcj.media.*
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener
import uk.co.caprica.vlcj.player.base.State
import uk.co.caprica.vlcj.player.component.AudioListPlayerComponent
import uk.co.caprica.vlcj.player.list.MediaListPlayer
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener
import java.io.File


class PlayerViewModel : ViewModel(), MediaListPlayerEventListener, MediaPlayerEventListener, MediaEventListener {

	private val audioListPlayer = AudioListPlayerComponent()

	private val _currentQueue: MutableStateFlow<List<File>> = MutableStateFlow(listOf())
	val currentQueue: StateFlow<List<File>> = _currentQueue

	private val _currentTrackIndex: MutableStateFlow<Int?> = MutableStateFlow(null)
	val currentTrackIndex: StateFlow<Int?> = _currentTrackIndex

	private val _isPlaying: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val isPlaying: StateFlow<Boolean> = _isPlaying

	private val _currentTrackLength: MutableStateFlow<Long> = MutableStateFlow(0)
	val currentTrackLength: StateFlow<Long> = _currentTrackLength
	private val _currentTrackPosition: MutableStateFlow<Long> = MutableStateFlow(0)
	val currentTrackPosition: StateFlow<Long> = _currentTrackPosition

	init {
		audioListPlayer.mediaPlayer().events().addMediaPlayerEventListener(this)
		audioListPlayer.mediaPlayer().events().addMediaEventListener(this)
		audioListPlayer.mediaListPlayer().events().addMediaListPlayerEventListener(this)
	}

	fun onClickFile(audioFileList: List<File>, audioFile: File) {
		viewModelScope.launch(Dispatchers.IO) {
			val nativeUri = NativeUri.encodeFileUri(audioFile.path)
			val queueIndex = _currentQueue.value.indexOf(audioFile)
			if (queueIndex == -1) {
				println("new queue")
				audioListPlayer.mediaListPlayer().controls().stop()
				audioListPlayer.mediaListPlayer().list().media().clear()
				audioFileList.forEach { audioListPlayer.mediaListPlayer().list().media().add(it.path) }
				_currentQueue.tryEmit(audioFileList)

				val index = audioListPlayer.mediaListPlayer().list().media().mrls().indexOf(nativeUri)
				audioListPlayer.mediaPlayer().controls().start()
				_currentTrackIndex.tryEmit(index)
			} else {
				println("same queue : $queueIndex")
				audioListPlayer.mediaListPlayer().controls().play(queueIndex)
				_currentTrackIndex.tryEmit(queueIndex)
			}
		}
	}

	fun clickPlayPause() {
		if (audioListPlayer.mediaPlayer().status().isPlaying) audioListPlayer.mediaPlayer().controls().pause()
		else audioListPlayer.mediaPlayer().controls().pause()
	}

	fun seekTo(seekTime: Long) {
		audioListPlayer.mediaPlayer().controls().setTime(seekTime)
	}

	fun skipTime(time: Long) {
		audioListPlayer.mediaPlayer().controls().skipTime(time)
	}

	fun playNext() {
		audioListPlayer.mediaListPlayer().controls().playNext()
	}

	fun playPrevious() {
		audioListPlayer.mediaListPlayer().controls().playPrevious()
	}

	fun playFromQueue(index: Int) {
		audioListPlayer.mediaListPlayer().controls().play(index)
	}

//	Implements

	override fun mediaChanged(mediaPlayer: MediaPlayer?, media: MediaRef?) {
		println("mediaChanged")
	}

	override fun opening(mediaPlayer: MediaPlayer?) {
		mediaPlayer?.media()?.info()?.mrl()?.let { println(it) }
	}

	override fun buffering(mediaPlayer: MediaPlayer?, newCache: Float) {
	}

	override fun playing(mediaPlayer: MediaPlayer?) {
		println("playing")
		_isPlaying.tryEmit(true)
		mediaPlayer?.media()?.info()?.mrl()?.let { println(it) }
	}

	override fun paused(mediaPlayer: MediaPlayer?) {
		println("paused")
		_isPlaying.tryEmit(false)
	}

	override fun stopped(mediaPlayer: MediaPlayer?) {
		println("stopped")
		_isPlaying.tryEmit(false)
	}

	override fun forward(mediaPlayer: MediaPlayer?) {
		println("forward")
	}

	override fun backward(mediaPlayer: MediaPlayer?) {
		println("backward")
	}

	override fun finished(mediaPlayer: MediaPlayer?) {
		println("finished")
	}

	override fun timeChanged(mediaPlayer: MediaPlayer?, newTime: Long) {
		_currentTrackPosition.tryEmit(newTime)
	}

	override fun positionChanged(mediaPlayer: MediaPlayer?, newPosition: Float) {
	}

	override fun seekableChanged(mediaPlayer: MediaPlayer?, newSeekable: Int) {
	}

	override fun pausableChanged(mediaPlayer: MediaPlayer?, newPausable: Int) {
	}

	override fun titleChanged(mediaPlayer: MediaPlayer?, newTitle: Int) {
	}

	override fun snapshotTaken(mediaPlayer: MediaPlayer?, filename: String?) {
	}

	override fun lengthChanged(mediaPlayer: MediaPlayer?, newLength: Long) {
		_currentTrackLength.tryEmit(newLength)
	}

	override fun videoOutput(mediaPlayer: MediaPlayer?, newCount: Int) {
	}

	override fun scrambledChanged(mediaPlayer: MediaPlayer?, newScrambled: Int) {
	}

	override fun elementaryStreamAdded(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) {
	}

	override fun elementaryStreamDeleted(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) {
	}

	override fun elementaryStreamSelected(mediaPlayer: MediaPlayer?, type: TrackType?, id: Int) {
	}

	override fun corked(mediaPlayer: MediaPlayer?, corked: Boolean) {
	}

	override fun muted(mediaPlayer: MediaPlayer?, muted: Boolean) {
	}

	override fun volumeChanged(mediaPlayer: MediaPlayer?, volume: Float) {
	}

	override fun audioDeviceChanged(mediaPlayer: MediaPlayer?, audioDevice: String?) {
	}

	override fun chapterChanged(mediaPlayer: MediaPlayer?, newChapter: Int) {
	}

	override fun error(mediaPlayer: MediaPlayer?) {
		println("error")
	}

	override fun mediaPlayerReady(mediaPlayer: MediaPlayer?) {
	}

	override fun mediaListPlayerFinished(mediaListPlayer: MediaListPlayer?) {
	}

	override fun nextItem(mediaListPlayer: MediaListPlayer?, item: MediaRef?) {
		item?.duplicateMedia()?.info()?.mrl()?.let {
			mediaListPlayer?.list()?.media()?.mrls()?.indexOf(it)?.let { _currentTrackIndex.tryEmit(it) }
		} ?: _currentTrackIndex.value?.let { _currentTrackIndex.tryEmit(it + 1) }
	}

	override fun stopped(mediaListPlayer: MediaListPlayer?) {
	}

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
	}
}
