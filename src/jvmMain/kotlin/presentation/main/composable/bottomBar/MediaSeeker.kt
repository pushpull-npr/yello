package presentation.main.composable.bottomBar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.component.shape.AbsoluteSmoothCornerShape
import util.TimeUtil.Companion.fromMilliToMinSec


@Preview
@Composable
fun MediaSeeker(
	modifier: Modifier = Modifier,
	currentTrackLength : Long = 47,
	currentTrackPosition : Long = 71,
	onSeekTime: (Float) -> Unit = {},
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center,
		modifier = modifier
	) {
		Spacer(modifier = Modifier.width(32.dp))
		Text(
			text = currentTrackPosition.fromMilliToMinSec(),
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier
				.wrapContentSize()
				.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(8.dp, 100))
				.padding(horizontal = 8.dp, vertical = 4.dp)
		)
		Spacer(modifier = Modifier.width(12.dp))
		Slider(
			value = currentTrackPosition.toFloat(),
			onValueChange = onSeekTime,
			valueRange = 0f..maxOf(currentTrackLength, currentTrackPosition).toFloat(),
			modifier = Modifier.weight(1f)
		)
		Spacer(modifier = Modifier.width(12.dp))
		Text(
			text = currentTrackLength.fromMilliToMinSec(),
			style = MaterialTheme.typography.bodySmall,
			color = MaterialTheme.colorScheme.onBackground,
			modifier = Modifier
				.wrapContentSize()
				.background(MaterialTheme.colorScheme.surface, AbsoluteSmoothCornerShape(8.dp, 100))
				.padding(horizontal = 8.dp, vertical = 4.dp)
		)
		Spacer(modifier = Modifier.width(32.dp))
	}
}
