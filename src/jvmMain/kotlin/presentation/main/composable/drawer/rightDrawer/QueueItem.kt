package presentation.main.composable.drawer.rightDrawer

import androidx.compose.animation.animateColorAsState
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jaudiotagger.audio.AudioHeader
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jetbrains.skia.Image
import presentation.component.shape.AbsoluteSmoothCornerShape
import util.AudioUtil
import util.TimeUtil.Companion.toMinSec
import java.io.File


@Preview
@Composable
fun QueueItem(
	audioFile: File,
	isCurrentTrack : Boolean = false,
	onClick : () -> Unit = {}
) {
	var tag by remember { mutableStateOf<Tag?>(null) }
	var audioHeader by remember { mutableStateOf<AudioHeader?>(null) }
	var albumImage by remember { mutableStateOf<ImageBitmap?>(null) }

	LaunchedEffect(key1 = audioFile) {
		try {
			AudioUtil.audioFileIO.readFile(audioFile).let {
				tag = it.tag
				audioHeader = it.audioHeader
			}
		} catch (e: Exception) {
			tag = null
			audioHeader = null
			albumImage = null
		}
		try {
			tag?.firstArtwork?.binaryData?.let { albumImage = Image.makeFromEncoded(it).toComposeImageBitmap() }
		} catch (_ : Exception) {
			albumImage = null
		}
	}

	val containerColor by animateColorAsState(if (isCurrentTrack) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background)
	val thumbnailColor  by animateColorAsState(if (isCurrentTrack) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.surface)

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.fillMaxWidth()
			.background(containerColor)
			.clickable { onClick() }
			.padding(horizontal = 16.dp, vertical = 8.dp)
	) {
		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.requiredSize(48.dp)
				.then(if (albumImage == null) Modifier.background(thumbnailColor, AbsoluteSmoothCornerShape(16.dp, 100)) else Modifier)
				.clip(AbsoluteSmoothCornerShape(16.dp, 100))
		) {
			albumImage?.let {
				Image(bitmap = it, contentDescription = null)
			} ?: Icon(
				painter = painterResource("icons/music.svg"),
				contentDescription = null,
				tint = contentColorFor(thumbnailColor),
				modifier = Modifier.requiredSize(24.dp)
			)
		}

		Spacer(modifier = Modifier.width(8.dp))

		Column(
			modifier = Modifier.weight(1f)
		) {
			Text(
				text = audioFile.name,
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurface,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
			)

			Text(
				text = tag?.getFirst(FieldKey.ARTIST) ?: "",
				style = MaterialTheme.typography.labelSmall,
				color = MaterialTheme.colorScheme.onSurface,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
			)
		}

		Text(
			text = audioHeader?.trackLength?.toMinSec() ?: "",
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurface,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
		)
	}
}
