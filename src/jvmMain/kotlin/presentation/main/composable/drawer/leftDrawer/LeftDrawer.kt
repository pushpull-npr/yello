package presentation.main.composable.drawer.leftDrawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import presentation.component.shape.AbsoluteSmoothCornerShape


@Composable
fun LeftDrawer(
	albumImage : ImageBitmap? = null
) {
	Column(
		horizontalAlignment = Alignment.CenterHorizontally,
		modifier = Modifier.fillMaxHeight()
	) {
		Spacer(modifier = Modifier.height(24.dp))
		DrawerItem(text = "Folder", icon = "icons/folder.svg", isSelected = true) {  }
		DrawerItem(text = "Album", icon = "icons/album.svg")
		DrawerItem(text = "Artist", icon = "icons/artist.svg")
		DrawerItem(text = "Playlist", icon = "icons/playlist.svg")

		Spacer(modifier = Modifier.weight(1f))

		DrawerItem(text = "Statistics", icon = "icons/statistics.svg")
		DrawerItem(text = "Settings", icon = "icons/settings.svg")

		Box(
			contentAlignment = Alignment.Center,
			modifier = Modifier
				.requiredSize(192.dp)
				.padding(24.dp)
				.then(if (albumImage == null) Modifier.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(32.dp, 100)) else Modifier)
				.clip(AbsoluteSmoothCornerShape(32.dp, 100))
		) {
			albumImage?.let {
				Image(bitmap = it, contentDescription = null)
			} ?: Icon(
				painter = painterResource("icons/music.svg"),
				contentDescription = null,
				tint = MaterialTheme.colorScheme.onSurface,
				modifier = Modifier.requiredSize(64.dp)
			)
		}
	}
}

@Composable
private fun DrawerItem(
	text : String = "Navigator",
	icon : String,
	isSelected : Boolean = false,
	onClick : () -> Unit = {},
) {
	Row (
		verticalAlignment = Alignment.CenterVertically,
		modifier = Modifier
			.width(192.dp)
			.height(64.dp)
			.padding(horizontal = 8.dp, vertical = 4.dp)
			.clip(MaterialTheme.shapes.medium)
			.clickable { onClick() }
			.padding(4.dp)
	) {

		Spacer(modifier = Modifier.width(24.dp))

		Box(
			modifier = Modifier
				.width(6.dp)
				.height(24.dp)
				.background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent, MaterialTheme.shapes.medium)
		)

		Spacer(modifier = Modifier.width(12.dp))

		Icon(
			painter = painterResource(icon),
			contentDescription = text,
			tint = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier.requiredSize(20.dp)
		)
		Spacer(Modifier.width(12.dp))
		Text(
			text = text,
			style = MaterialTheme.typography.bodyMedium,
			color = MaterialTheme.colorScheme.onBackground
		)

		Spacer(modifier = Modifier.width(16.dp))
	}
}
