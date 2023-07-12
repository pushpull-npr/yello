package presentation.component.button

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import presentation.component.shape.AbsoluteSmoothCornerShape


@Preview
@Composable
fun InterfaceButton(
	modifier: Modifier = Modifier,
	icon: String,
	contentDescription: String? = null,
	interfaceButtonSize: InterfaceButtonSize = InterfaceButtonSize.Normal,
	enabled: Boolean = true,
	onClick: () -> Unit = {}
) {
	FilledIconButton(
		onClick = onClick,
		colors = IconButtonDefaults.filledIconButtonColors(
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.onSurface
		),
		shape = AbsoluteSmoothCornerShape(
			when (interfaceButtonSize) {
				InterfaceButtonSize.Normal -> 12.dp
				InterfaceButtonSize.Large -> 16.dp
			}, 100
		),
		enabled = enabled,
		modifier = modifier
			.requiredSize(
				when (interfaceButtonSize) {
					InterfaceButtonSize.Normal -> 48.dp
					InterfaceButtonSize.Large -> 64.dp
				}
			)
			.padding(4.dp)
	) {
		Icon(
			painter = painterResource(icon),
			contentDescription = contentDescription,
			modifier = Modifier
				.requiredSize(
					when (interfaceButtonSize) {
						InterfaceButtonSize.Normal -> 20.dp
						InterfaceButtonSize.Large -> 32.dp
					}
				),
		)
	}
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun AnimatedInterfaceButton(
	modifier: Modifier = Modifier,
	icon: String,
	contentDescription: String? = null,
	interfaceButtonSize: InterfaceButtonSize = InterfaceButtonSize.Normal,
	enabled: Boolean = true,
	onClick: () -> Unit = {}
) {
	FilledIconButton(
		onClick = onClick,
		colors = IconButtonDefaults.filledIconButtonColors(
			containerColor = MaterialTheme.colorScheme.surface,
			contentColor = MaterialTheme.colorScheme.onSurface
		),
		shape = AbsoluteSmoothCornerShape(
			when (interfaceButtonSize) {
				InterfaceButtonSize.Normal -> 12.dp
				InterfaceButtonSize.Large -> 16.dp
			}, 100
		),
		enabled = enabled,
		modifier = modifier
			.requiredSize(
				when (interfaceButtonSize) {
					InterfaceButtonSize.Normal -> 48.dp
					InterfaceButtonSize.Large -> 64.dp
				}
			)
			.padding(4.dp)
	) {
		AnimatedContent(
			targetState = icon,
			transitionSpec = { fadeIn(tween(470)) + scaleIn(tween(470)) with fadeOut(tween(470)) + scaleOut(tween(470)) }
		) {
			Icon(
				painter = painterResource(it),
				contentDescription = contentDescription,
				modifier = Modifier
					.requiredSize(
						when (interfaceButtonSize) {
							InterfaceButtonSize.Normal -> 20.dp
							InterfaceButtonSize.Large -> 32.dp
						}
					)
			)
		}
	}
}

enum class InterfaceButtonSize {
	Normal,
	Large,
}

