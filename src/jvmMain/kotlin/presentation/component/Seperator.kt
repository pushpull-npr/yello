package presentation.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


enum class SeperatorOrientation {
	Horizontal,
	Verticle
}

@Preview
@Composable
fun Seperator(
	modifier: Modifier = Modifier,
	weight: Float = 1f,
	separatorOrientation: SeperatorOrientation = SeperatorOrientation.Horizontal,
) {
	if (separatorOrientation == SeperatorOrientation.Horizontal) {
		Spacer(
			modifier = Modifier
				.fillMaxWidth(weight)
				.height(1.dp)
				.background(MaterialTheme.colorScheme.surface)
		)
	} else {
		Spacer(
			modifier = Modifier
				.width(1.dp)
				.fillMaxHeight(weight)
				.background(MaterialTheme.colorScheme.surface)
		)
	}
}

@Preview
@Composable
fun Hr(
	modifier: Modifier = Modifier,
	weight: Float = 1f,
) {
	Seperator(modifier = modifier, weight = weight, separatorOrientation = SeperatorOrientation.Horizontal)
}

@Preview
@Composable
fun Vc(
	modifier: Modifier = Modifier,
	weight: Float = 1f,
) {
	Seperator(modifier = modifier, weight = weight, separatorOrientation = SeperatorOrientation.Verticle)
}
