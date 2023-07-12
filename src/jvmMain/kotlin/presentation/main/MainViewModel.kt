package presentation.main

import data.settings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import moe.tlaster.precompose.viewmodel.ViewModel
import moe.tlaster.precompose.viewmodel.viewModelScope
import org.apache.commons.io.FileUtils
import org.jaudiotagger.tag.FieldKey
import util.AudioUtil
import util.SortUtil
import java.io.File


class MainViewModel : ViewModel() {

	private val _currentFolder: MutableStateFlow<File?> = MutableStateFlow(null)
	val currentFolder: StateFlow<File?> = _currentFolder

	private val _childFolderList: MutableStateFlow<List<File>> = MutableStateFlow(listOf())
	val childFolderList: StateFlow<List<File>> = _childFolderList

	private val _audioFileList: MutableStateFlow<List<File>> = MutableStateFlow(listOf())
	val audioFileList: StateFlow<List<File>> = _audioFileList

	private val _sortBy: MutableStateFlow<SortUtil.Companion.SortBy> = MutableStateFlow(SortUtil.Companion.SortBy.TrackNumber)
	val sortBy: StateFlow<SortUtil.Companion.SortBy> = _sortBy
	private val _sortOrder: MutableStateFlow<SortUtil.Companion.SortOrder> = MutableStateFlow(SortUtil.Companion.SortOrder.Ascending)
	val sortOrder: StateFlow<SortUtil.Companion.SortOrder> = _sortOrder

	init {
		viewModelScope.launch(Dispatchers.IO) {
			combine(currentFolder, sortBy, sortOrder) { currentFolder1, sortBy1, sortOrder1 ->
				settings.putString("filePath", currentFolder1?.path ?: "")
				currentFolder1?.let { currentFolder2 ->
					val childFolderList = (currentFolder2.listFiles()?.filter { it.isDirectory }?.let {
						if (sortOrder1 == SortUtil.Companion.SortOrder.Ascending) it.sortedBy { it.name } else it.sortedByDescending { it.name }
					} ?: listOf())

					val childFileList = (FileUtils.listFiles(currentFolder2, arrayOf("mp3", "ogg", "opus", "wav"), false)?.let {
						if (sortOrder1 == SortUtil.Companion.SortOrder.Ascending) {
							when (sortBy1) {
								SortUtil.Companion.SortBy.TrackNumber -> it.sortedBy {
									try {
										AudioUtil.audioFileIO.readFile(it).tag.getFirst(FieldKey.TRACK).toInt()
									} catch (_: Exception) {
										0
									}
								}

								SortUtil.Companion.SortBy.Name -> it.sortedBy { it.name }
								SortUtil.Companion.SortBy.TrackLength -> it.sortedBy {
									try {
										AudioUtil.audioFileIO.readFile(it).audioHeader.trackLength
									} catch (_: Exception) {
										0
									}
								}
							}
						} else {
							when (sortBy1) {
								SortUtil.Companion.SortBy.TrackNumber -> it.sortedByDescending {
									try {
										AudioUtil.audioFileIO.readFile(it).tag.getFirst(FieldKey.TRACK).toInt()
									} catch (_: Exception) {
										0
									}
								}

								SortUtil.Companion.SortBy.Name -> it.sortedByDescending { it.name }
								SortUtil.Companion.SortBy.TrackLength -> it.sortedByDescending {
									try {
										AudioUtil.audioFileIO.readFile(it).audioHeader.trackLength
									} catch (_: Exception) {
										0
									}
								}
							}
						}
					} ?: listOf())
					Pair(childFolderList, childFileList)
				} ?: Pair(listOf(), listOf())
			}.collect { (childFolderList, childFileList) ->
				_childFolderList.tryEmit(childFolderList)
				_audioFileList.tryEmit(childFileList)
				println(childFolderList.size)
				println(childFileList.size)
			}
		}

		viewModelScope.launch(Dispatchers.IO) {
			settings.getString("filePath", "").let {
				if (it.isNotEmpty()) scanAndReadFolder(File(it))
			}
		}
	}

	fun goUpInFolder() {
		_currentFolder.value?.parentFile?.let { scanAndReadFolder(file = it) }
	}

	fun scanAndReadFolder(file: File) {
		_currentFolder.tryEmit(file)
	}

	fun updateSortBy(sortBy: SortUtil.Companion.SortBy) {
		_sortBy.tryEmit(sortBy)
	}

	fun updateSortOrder(sortOrder: SortUtil.Companion.SortOrder) {
		_sortOrder.tryEmit(sortOrder)
	}
}
