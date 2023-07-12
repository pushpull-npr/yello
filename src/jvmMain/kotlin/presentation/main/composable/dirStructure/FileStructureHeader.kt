package presentation.main.composable.dirStructure

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import presentation.component.Hr
import presentation.component.button.InterfaceButton
import presentation.component.dropdown.YelloDropdownItem
import presentation.component.shape.AbsoluteSmoothCornerShape
import util.SortUtil
import java.io.File


@Preview
@Composable
fun FileStructureHeader(
	currentFolder: File,
	sortBy: SortUtil.Companion.SortBy,
	sortOrder: SortUtil.Companion.SortOrder,
	onClickGoUp: () -> Unit = {},
	onClickGoBack: () -> Unit = {},
	onClickGoForward: () -> Unit = {},
	onUpdateSortBy: (SortUtil.Companion.SortBy) -> Unit = {},
	onUpdateSortOrder: (SortUtil.Companion.SortOrder) -> Unit = {},
) {
	val scope = rememberCoroutineScope()

	var parentFolderList by remember { mutableStateOf<List<String>>(listOf()) }
	LaunchedEffect(key1 = currentFolder) {
		scope.launch(Dispatchers.IO) {
			val newParentFolderList = mutableListOf<String>()
			var pointedFolder: File? = currentFolder
			while (pointedFolder != null) {
				newParentFolderList.add(pointedFolder.name)
				pointedFolder = pointedFolder.parentFile
			}

			newParentFolderList.removeLastOrNull()
			parentFolderList = newParentFolderList
		}
	}

	var isFilterMenuVisible by remember { mutableStateOf(false) }

	Row(
		modifier = Modifier,
		verticalAlignment = Alignment.CenterVertically
	) {
		NavigationButtons(
			onClickGoUp = onClickGoUp,
			onClickGoBack = onClickGoBack,
			onClickGoForward = onClickGoForward
		)

		Spacer(modifier = Modifier.width(12.dp))

		Row(
			verticalAlignment = Alignment.CenterVertically,
			modifier = Modifier
				.height(40.dp)
				.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(8.dp, 100))
				.padding(horizontal = 12.dp)
		) {
			Icon(
				painter = painterResource("icons/folder.svg"),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.requiredSize(24.dp)
			)
			Spacer(modifier = Modifier.width(8.dp))
			parentFolderList.reversed().forEach { folder ->
				Text(
					text = "/",
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface
				)
				Text(
					text = folder,
					style = MaterialTheme.typography.titleMedium,
					color = MaterialTheme.colorScheme.onSurface,
					modifier = Modifier
						.padding(2.dp)
						.clip(AbsoluteSmoothCornerShape(4.dp, 100))
						.clickable { }
						.padding(horizontal = 4.dp, vertical = 4.dp)
				)
			}
		}

		Spacer(modifier = Modifier.weight(1f))

		Button(
			onClick = { isFilterMenuVisible = true },
			modifier = Modifier.height(40.dp),
			colors = ButtonDefaults.buttonColors(
				containerColor = MaterialTheme.colorScheme.surface,
				contentColor = MaterialTheme.colorScheme.onSurface
			),
			shape = AbsoluteSmoothCornerShape(12.dp, 100)
		) {
			Icon(
				painter = painterResource("icons/sort.svg"),
				contentDescription = "Sort",
				modifier = Modifier.requiredSize(20.dp),
				tint = MaterialTheme.colorScheme.onSurface,
			)
			Spacer(modifier = Modifier.width(8.dp))
			Text(
				text = "${sortBy.visibleName} - ${sortOrder.name}",
				color = MaterialTheme.colorScheme.onSurface,
			)
		}

		InterfaceButton(
			icon = "icons/search.svg",
			contentDescription = "Search for songs or folder",
			onClick = {}
		)
	}

	DropdownMenu(
		expanded = isFilterMenuVisible,
		onDismissRequest = { isFilterMenuVisible = false },
		modifier = Modifier.background(MaterialTheme.colorScheme.surface)
	) {
		YelloDropdownItem(text = "Track number", icon = "icons/sort_track_number.svg") { onUpdateSortBy(SortUtil.Companion.SortBy.TrackNumber) }
		YelloDropdownItem(text = "Name", icon = "icons/sort_track_number.svg") { onUpdateSortBy(SortUtil.Companion.SortBy.Name) }
		YelloDropdownItem(text = "Track length", icon = "icons/sort_track_number.svg") { onUpdateSortBy(SortUtil.Companion.SortBy.TrackLength) }
		Hr()
		YelloDropdownItem(text = "Ascending", icon = "icons/sort_ascending.svg") { onUpdateSortOrder(SortUtil.Companion.SortOrder.Ascending) }
		YelloDropdownItem(text = "Descending", icon = "icons/sort_descending.svg") { onUpdateSortOrder(SortUtil.Companion.SortOrder.Descending) }
	}
}

@Preview
@Composable
private fun NavigationButtons(
	onClickGoUp: () -> Unit = {},
	onClickGoBack: () -> Unit = {},
	onClickGoForward: () -> Unit = {},
) {
	Row {
		InterfaceButton(
			icon = "icons/arrow.svg",
			contentDescription = "Go up",
			onClick = onClickGoUp,
			modifier = Modifier.graphicsLayer { rotationZ = 90f }
		)
		Spacer(modifier = Modifier.width(4.dp))
		InterfaceButton(
			icon = "icons/caret.svg",
			contentDescription = "Go back",
			onClick = onClickGoBack,
		)
		InterfaceButton(
			icon = "icons/caret.svg",
			contentDescription = "Go forward",
			onClick = onClickGoForward,
			modifier = Modifier.graphicsLayer { rotationZ = 180f }
		)
	}
}
