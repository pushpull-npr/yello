package presentation.main.composable.dirStructure

import LocalWindow
import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.viewmodel.viewModel
import presentation.component.Hr
import presentation.main.MainViewModel
import presentation.main.PlayerViewModel
import java.io.File
import javax.swing.JFileChooser


@OptIn(ExperimentalAnimationApi::class, ExperimentalFoundationApi::class)
@Preview
@Composable
fun FileStructure(
	modifier: Modifier = Modifier,
	mainViewModel: MainViewModel = viewModel(MainViewModel::class) { MainViewModel() },
	playerViewModel: PlayerViewModel = viewModel(PlayerViewModel::class) { PlayerViewModel() },
) {
	val window = LocalWindow.current

	val currentFolder by mainViewModel.currentFolder.collectAsState()
	val folderList by mainViewModel.childFolderList.collectAsState()
	val audioFileList by mainViewModel.audioFileList.collectAsState()

	val lazyListState = rememberLazyListState()
	val scrollBarAdapter = rememberScrollbarAdapter(lazyListState)

	val elementCountInRow by remember(window.width) { derivedStateOf { window.width / 192 } }
	val columnCount by remember(folderList, window.width) { derivedStateOf { folderList.size / (maxOf(1, window.width / 192)) + 1 } }

	val sortBy by mainViewModel.sortBy.collectAsState()
	val sortOrder by mainViewModel.sortOrder.collectAsState()

	AnimatedContent(
		targetState = currentFolder,
		transitionSpec = { fadeIn(tween(470)) with fadeOut(tween(470)) },
		modifier = modifier
	) {
		if (it == null) {
			EmptyFolderView { mainViewModel.scanAndReadFolder(it) }
		} else {
			Row(
				modifier = Modifier
			) {
				Column(
					modifier = Modifier
						.weight(1f)
						.padding(8.dp)
				) {
					FileStructureHeader(
						currentFolder = it,
						sortBy = sortBy,
						sortOrder = sortOrder,
						onClickGoUp = { mainViewModel.goUpInFolder() },
						onClickGoBack = {},
						onClickGoForward = {},
						onUpdateSortBy = { mainViewModel.updateSortBy(it) },
						onUpdateSortOrder = { mainViewModel.updateSortOrder(it) },
					)
					Spacer(modifier = Modifier.height(8.dp))
					LazyColumn(
						state = lazyListState,
						modifier = Modifier
							.fillMaxWidth()
							.weight(1f)
					) {
						for (i in 0 until columnCount) {
							item(
								contentType = 0
							) {
								Row(
									modifier = Modifier.fillMaxWidth()
								) {
									for (j in 0 until elementCountInRow) {
										val folder = folderList.getOrNull(i * elementCountInRow + j)
										folder?.let { folder1 ->
											FolderItem(
												file = folder1,
												onClickFolder = { mainViewModel.scanAndReadFolder(folder1) }
											)
										} ?: Box(modifier = Modifier.weight(1f))
									}
								}
							}
						}

						item(contentType = 1) { Spacer(modifier = Modifier.height(8.dp)) }

						stickyHeader { AudioFileListHeader() }
						audioFileList.forEachIndexed { index, audioFile ->
							item(
								contentType = 3
							) {
								AudioFileItem(
									audioFile = audioFile,
								) { playerViewModel.onClickFile(audioFileList, audioFile) }
							}
						}
					}
				}

				VerticalScrollbar(
					adapter = scrollBarAdapter,
					style = ScrollbarStyle(
						minimalHeight = 32.dp,
						thickness = 12.dp,
						shape = RoundedCornerShape(4.dp),
						hoverDurationMillis = 470,
						unhoverColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.47f),
						hoverColor = MaterialTheme.colorScheme.onBackground
					),
					modifier = Modifier
						.fillMaxHeight()
						.padding(2.dp)
				)
			}
		}
	}
}

@Preview
@Composable
private fun EmptyFolderView(
	onSelectFolder: (File) -> Unit,
) {
	val window = LocalWindow.current

	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		verticalArrangement = Arrangement.Center,
		modifier = Modifier.fillMaxSize()
	) {
		Icon(
			painter = painterResource("icons/select_folder.svg"),
			contentDescription = "Select folder",
			tint = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.47f),
			modifier = Modifier.requiredSize(128.dp)
		)
		Spacer(modifier = Modifier.height(8.dp))
		Button(
			onClick = {
				val jFileChooser = JFileChooser()
				jFileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
				val returnResult = jFileChooser.showOpenDialog(window)
				if (returnResult == JFileChooser.APPROVE_OPTION) {
					onSelectFolder(jFileChooser.selectedFile)
				}
			},
			shape = MaterialTheme.shapes.medium,
		) {
			Text(
				text = "Select folder"
			)
		}
	}
}

@Preview
@Composable
private fun AudioFileListHeader() {
	Column(
		modifier = Modifier
			.fillMaxWidth()
			.background(MaterialTheme.colorScheme.background)
	) {
		Spacer(modifier = Modifier.height(16.dp))
		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier.fillMaxWidth()
		) {
			Spacer(modifier = Modifier.width(12.dp))
			Text(
				text = "Track no",
				style = MaterialTheme.typography.bodyMedium,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				modifier = Modifier.width(88.dp)
			)

			Spacer(modifier = Modifier.width(24.dp))

			Text(
				text = "Title",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.weight(1f)
			)

			Spacer(modifier = Modifier.width(12.dp))

			Text(
				text = "Type",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.width(48.dp),
				textAlign = TextAlign.Center
			)

			Spacer(modifier = Modifier.width(8.dp))
			Text(
				text = "Artist",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.width(192.dp)
			)

			Spacer(modifier = Modifier.width(12.dp))

			Text(
				text = "Length",
				style = MaterialTheme.typography.bodyMedium,
				modifier = Modifier.width(64.dp)
			)

			Spacer(modifier = Modifier.width(16.dp))

			Spacer(modifier = Modifier.width(48.dp))

			Spacer(modifier = Modifier.width(16.dp))
		}

		Spacer(modifier = Modifier.height(16.dp))
		Hr()
		Spacer(modifier = Modifier.height(8.dp))
	}
}
