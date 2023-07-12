package presentation.main.composable.drawer.rightDrawer

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.io.File


@Preview
@Composable
fun RightDrawer(
	currentQueue: List<File> = listOf(),
	currentTrackIndex: Int? = null,
	onClickFile: (Int) -> Unit = {}
) {
	Column(
		modifier = Modifier
			.width(384.dp)
			.fillMaxHeight()
			.background(MaterialTheme.colorScheme.surface.copy(alpha = 0.071f))
	) {
		Spacer(modifier = Modifier.height(12.dp))
		Text(
			text = "Current queue",
			style = MaterialTheme.typography.titleMedium,
			color = MaterialTheme.colorScheme.onSurface,
			modifier = Modifier.padding(horizontal = 12.dp)
		)
		Spacer(modifier = Modifier.height(16.dp))
		LazyColumn {
			item {
				currentQueue.forEachIndexed { index, file ->
					QueueItem(
						audioFile = file,
						isCurrentTrack = currentTrackIndex == index,
						onClick = { onClickFile(index) }
					)
				}
			}
		}
	}
}
