package presentation.main.composable.dirStructure

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.apache.commons.io.FileUtils
import presentation.component.shape.AbsoluteSmoothCornerShape
import java.io.File
import java.io.UncheckedIOException


@Preview
@Composable
fun RowScope.FolderItem(
	file: File,
	onClickFolder: () -> Unit = {},
) {
	val scope = rememberCoroutineScope()

	var childSize by remember { mutableStateOf(0) }

	LaunchedEffect(key1 = Unit) {
		scope.launch(Dispatchers.IO) {
			try {
				childSize = FileUtils.listFiles(file, arrayOf("mp3", "ogg", "opus", "wav", "m4a"), true).size
			} catch (_ : AccessDeniedException) {
			} catch (_ : UncheckedIOException) {
			}
		}
	}

	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.weight(1f)
			.padding(8.dp)
			.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(16.dp, 100))
			.clip(AbsoluteSmoothCornerShape(16.dp, 100))
			.clickable { onClickFolder() }
			.padding(horizontal = 16.dp, vertical = 16.dp)
	) {
		Icon(
			painter = painterResource("icons/folder.svg"),
			contentDescription = null,
			modifier = Modifier.requiredSize(24.dp)
		)
		Spacer(modifier = Modifier.width(12.dp))
		Column {
			Text(
				text = file.nameWithoutExtension,
				style = MaterialTheme.typography.titleSmall,
				color = MaterialTheme.colorScheme.onSurface,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis
			)
			Text(
				text = "$childSize",
				style = MaterialTheme.typography.bodyMedium,
				color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.47f),
//				fontWeight = FontWeight.Bold,
			)
		}
	}
}
