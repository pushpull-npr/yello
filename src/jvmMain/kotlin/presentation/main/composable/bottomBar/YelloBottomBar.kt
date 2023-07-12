package presentation.main.composable.bottomBar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.viewmodel.viewModel
import presentation.main.PlayerViewModel


@Preview
@Composable
fun YelloBottomBar(
	playerViewModel: PlayerViewModel = viewModel(PlayerViewModel::class) { PlayerViewModel() },
	title: String? = null,
	artist: String? = null,
) {
	val density = LocalDensity.current
	var mediaControllerWidth by remember { mutableStateOf<Int?>(null) }

	val isPlaying by playerViewModel.isPlaying.collectAsState()
	val currentTrackLength by playerViewModel.currentTrackLength.collectAsState()
	val currentTrackPosition by playerViewModel.currentTrackPosition.collectAsState()

	val currentQueue by playerViewModel.currentQueue.collectAsState()
	val currentTrackIndex by playerViewModel.currentTrackIndex.collectAsState()

	Column(
		verticalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxWidth()
			.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.071f))
			.padding(vertical = 12.dp)
	) {
		Row(
			verticalAlignment = Alignment.CenterVertically,
			horizontalArrangement = Arrangement.SpaceBetween,
			modifier = Modifier
				.fillMaxWidth()
				.padding(horizontal = 32.dp)
		) {
			mediaControllerWidth?.let {
				Box(
					modifier = Modifier.width(with(density) { it.toDp() })
				) {
					TitleArtistView(
						title = title,
						artist = artist,
					)
				}
			}
			MediaSeeker(
				currentTrackLength = currentTrackLength,
				currentTrackPosition = currentTrackPosition,
				onSeekTime = { playerViewModel.seekTo(it.toLong()) },
				modifier = Modifier.widthIn(128.dp, 576.dp)
			)
			MediaController(
				isPlaying = isPlaying,
				modifier = Modifier.onGloballyPositioned {
					mediaControllerWidth = it.size.width
				},
				onClickPlayPrevious = { playerViewModel.playPrevious() },
				onClickPlayNext = { playerViewModel.playNext() },
				onClickSkipTimeBackward = { playerViewModel.skipTime(-10000) },
				onClickSkipTimeForeward = { playerViewModel.skipTime(10000) },
				onClickPlayPause = { playerViewModel.clickPlayPause() },
			)
		}
	}
}

@Preview
@Composable
private fun TitleArtistView(
	title : String? = null,
	artist : String? = null,
) {
	Column(
		modifier = Modifier
	) {
		Text(
			text = title ?: "",
			style = MaterialTheme.typography.titleMedium,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
		)
		Text(
			text = artist ?: "",
			style = MaterialTheme.typography.bodySmall
		)
	}
}
