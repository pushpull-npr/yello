package presentation.main.composable.bottomBar

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import presentation.component.button.AnimatedInterfaceButton
import presentation.component.button.InterfaceButton
import presentation.component.button.InterfaceButtonSize


@Preview
@Composable
fun MediaController(
	modifier: Modifier = Modifier,
	isPlaying : Boolean = false,
	isPlayPauseButtonEnabled : Boolean = true,
	onClickPlayPrevious: () -> Unit = {},
	onClickPlayNext: () -> Unit = {},
	onClickSkipTimeBackward: () -> Unit = {},
	onClickSkipTimeForeward: () -> Unit = {},
	onClickPlayPause: () -> Unit = {}
) {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		modifier = modifier,
	) {
		InterfaceButton(
			icon = "icons/media_play_previous.svg",
			onClick = onClickPlayPrevious,
		)
		InterfaceButton(
			icon = "icons/media_skip_time_backward.svg",
			onClick = onClickSkipTimeBackward
		)
		AnimatedInterfaceButton(
			icon = if (isPlaying) "icons/media_pause.svg" else "icons/media_play.svg",
			interfaceButtonSize = InterfaceButtonSize.Large,
			onClick = onClickPlayPause,
		)
		InterfaceButton(
			icon = "icons/media_skip_time_forward.svg",
			onClick = onClickSkipTimeForeward
		)
		InterfaceButton(
			icon = "icons/media_play_next.svg",
			onClick = onClickPlayNext
		)
	}
}
