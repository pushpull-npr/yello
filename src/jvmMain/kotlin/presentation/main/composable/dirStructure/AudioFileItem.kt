package presentation.main.composable.dirStructure

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jaudiotagger.audio.AudioHeader
import org.jaudiotagger.tag.FieldKey
import org.jaudiotagger.tag.Tag
import org.jetbrains.skia.Image
import presentation.component.shape.AbsoluteSmoothCornerShape
import singleton.AudioUtil
import util.TimeUtil.Companion.toMinSec
import java.io.File


@Preview
@Composable
fun AudioFileItem(
	audioFile: File,
	index: Int = 1,
	onClickFile: () -> Unit = {},
) {
	var audioTag by remember { mutableStateOf<Tag?>(null) }
	var audioHeader by remember { mutableStateOf<AudioHeader?>(null) }
	var albumImage by remember { mutableStateOf<ImageBitmap?>(null) }

	LaunchedEffect(key1 = audioFile) {
		try {
			AudioUtil.audioFileIO.readFile(audioFile).let {
				audioTag = it.tag
				audioHeader = it.audioHeader
			}
		} catch (e: Exception) {
			audioTag = null
			audioHeader = null
			albumImage = null
		}
		try {
			audioTag?.firstArtwork?.binaryData?.let { albumImage = Image.makeFromEncoded(it).toComposeImageBitmap() }
		} catch (_ : Exception) {
			albumImage = null
		}
	}

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier.fillMaxWidth()
			.clickable { onClickFile() }
			.padding(vertical = 8.dp)
	) {
		Spacer(modifier = Modifier.width(12.dp))
		Text(
			text = audioTag?.getFirst(FieldKey.TRACK) ?: "",
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.71f),
			modifier = Modifier.width(24.dp)
		)

		Spacer(modifier = Modifier.width(16.dp))

		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.requiredSize(48.dp)
				.then(if (albumImage == null) Modifier.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(16.dp, 100)) else Modifier)
				.clip(AbsoluteSmoothCornerShape(16.dp, 100))
		) {
			albumImage?.let {
				Image(bitmap = it, contentDescription = null)
			} ?: Icon(
				painter = painterResource("icons/music.svg"),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.requiredSize(24.dp)
			)
		}

		Spacer(modifier = Modifier.width(24.dp))

//		Title
		Text(
			text = audioFile.name,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurface,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.weight(1f)
		)

		Spacer(modifier = Modifier.width(12.dp))

//		File type mp3, opus, wav
		Text(
			text = audioFile.extension,
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onSurface,
			textAlign = TextAlign.Center,
			modifier = Modifier
				.width(48.dp)
				.background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.small)
				.padding(horizontal = 8.dp, vertical = 4.dp)
		)

		Spacer(modifier = Modifier.width(8.dp))

//		Artist
		Text(
			text = audioTag?.getFirst(FieldKey.ARTIST) ?: "",
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurface,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.width(192.dp)
		)
		Spacer(modifier = Modifier.width(8.dp))

//		Track length
		Text(
			text = audioHeader?.trackLength?.toMinSec() ?: "",
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onSurface,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			modifier = Modifier.width(64.dp)
		)

		Spacer(modifier = Modifier.width(16.dp))

		IconButton(
			onClick = {}
		) {
			Icon(
				painter = painterResource("icons/menu.svg"),
				contentDescription = null,
				modifier = Modifier.requiredSize(16.dp)
			)
		}

		Spacer(modifier = Modifier.width(16.dp))
	}
}
