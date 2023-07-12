package theme


import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.WindowSize

val md_theme_light_primary = Color(0xFF7D5800)
val md_theme_light_onPrimary = Color(0xFFFFFFFF)
val md_theme_light_primaryContainer = Color(0xFFFFDEA9)
val md_theme_light_onPrimaryContainer = Color(0xFF271900)
val md_theme_light_secondary = Color(0xFF6D5C3F)
val md_theme_light_onSecondary = Color(0xFFFFFFFF)
val md_theme_light_secondaryContainer = Color(0xFFF7DFBB)
val md_theme_light_onSecondaryContainer = Color(0xFF251A04)
val md_theme_light_tertiary = Color(0xFF4D6544)
val md_theme_light_onTertiary = Color(0xFFFFFFFF)
val md_theme_light_tertiaryContainer = Color(0xFFCFEBC0)
val md_theme_light_onTertiaryContainer = Color(0xFF0B2006)
val md_theme_light_error = Color(0xFFBA1A1A)
val md_theme_light_errorContainer = Color(0xFFFFDAD6)
val md_theme_light_onError = Color(0xFFFFFFFF)
val md_theme_light_onErrorContainer = Color(0xFF410002)
val md_theme_light_background = Color(0xFFFFFBFF)
val md_theme_light_onBackground = Color(0xFF1F1B16)
val md_theme_light_surface = Color(0xFFFFFBFF)
val md_theme_light_onSurface = Color(0xFF1F1B16)
val md_theme_light_surfaceVariant = Color(0xFFEEE1CF)
val md_theme_light_onSurfaceVariant = Color(0xFF4E4639)
val md_theme_light_outline = Color(0xFF807667)
val md_theme_light_inverseOnSurface = Color(0xFFF8EFE7)
val md_theme_light_inverseSurface = Color(0xFF34302A)
val md_theme_light_inversePrimary = Color(0xFFFFBA27)
val md_theme_light_shadow = Color(0xFF000000)
val md_theme_light_surfaceTint = Color(0xFF7D5800)
val md_theme_light_outlineVariant = Color(0xFFD1C5B4)
val md_theme_light_scrim = Color(0xFF000000)

val md_theme_dark_primary = Color(0xFFFFBA27)
val md_theme_dark_onPrimary = Color(0xFF422D00)
val md_theme_dark_primaryContainer = Color(0xFF5E4100)
val md_theme_dark_onPrimaryContainer = Color(0xFFFFDEA9)
val md_theme_dark_secondary = Color(0xFFDAC3A1)
val md_theme_dark_onSecondary = Color(0xFF3C2E16)
val md_theme_dark_secondaryContainer = Color(0xFF54442A)
val md_theme_dark_onSecondaryContainer = Color(0xFFF7DFBB)
val md_theme_dark_tertiary = Color(0xFFB3CEA6)
val md_theme_dark_onTertiary = Color(0xFF203619)
val md_theme_dark_tertiaryContainer = Color(0xFF364D2E)
val md_theme_dark_onTertiaryContainer = Color(0xFFCFEBC0)
val md_theme_dark_error = Color(0xFFFFB4AB)
val md_theme_dark_errorContainer = Color(0xFF93000A)
val md_theme_dark_onError = Color(0xFF690005)
val md_theme_dark_onErrorContainer = Color(0xFFFFDAD6)
val md_theme_dark_background = Color(0xFF1F1B16)
val md_theme_dark_onBackground = Color(0xFFEAE1D9)
val md_theme_dark_surface = Color(0xFF1F1B16)
val md_theme_dark_onSurface = Color(0xFFEAE1D9)
val md_theme_dark_surfaceVariant = Color(0xFF4E4639)
val md_theme_dark_onSurfaceVariant = Color(0xFFD1C5B4)
val md_theme_dark_outline = Color(0xFF9A8F80)
val md_theme_dark_inverseOnSurface = Color(0xFF1F1B16)
val md_theme_dark_inverseSurface = Color(0xFFEAE1D9)
val md_theme_dark_inversePrimary = Color(0xFF7D5800)
val md_theme_dark_shadow = Color(0xFF000000)
val md_theme_dark_surfaceTint = Color(0xFFFFBA27)
val md_theme_dark_outlineVariant = Color(0xFF4E4639)
val md_theme_dark_scrim = Color(0xFF000000)


val seed = Color(0xFFFFD178)

val LightColors = lightColorScheme(
	primary = md_theme_light_primary,
	onPrimary = md_theme_light_onPrimary,
	primaryContainer = md_theme_light_primaryContainer,
	onPrimaryContainer = md_theme_light_onPrimaryContainer,
	secondary = md_theme_light_secondary,
	onSecondary = md_theme_light_onSecondary,
	secondaryContainer = md_theme_light_secondaryContainer,
	onSecondaryContainer = md_theme_light_onSecondaryContainer,
	tertiary = md_theme_light_tertiary,
	onTertiary = md_theme_light_onTertiary,
	tertiaryContainer = md_theme_light_tertiaryContainer,
	onTertiaryContainer = md_theme_light_onTertiaryContainer,
	error = md_theme_light_error,
	errorContainer = md_theme_light_errorContainer,
	onError = md_theme_light_onError,
	onErrorContainer = md_theme_light_onErrorContainer,
	background = md_theme_light_background,
	onBackground = md_theme_light_onBackground,
	surface = md_theme_light_surface,
	onSurface = md_theme_light_onSurface,
	surfaceVariant = md_theme_light_surfaceVariant,
	onSurfaceVariant = md_theme_light_onSurfaceVariant,
	outline = md_theme_light_outline,
	inverseOnSurface = md_theme_light_inverseOnSurface,
	inverseSurface = md_theme_light_inverseSurface,
	inversePrimary = md_theme_light_inversePrimary,
	surfaceTint = md_theme_light_surfaceTint,
	outlineVariant = md_theme_light_outlineVariant,
	scrim = md_theme_light_scrim,
)


val DarkColors = darkColorScheme(
	primary = md_theme_light_primary,
	onPrimary = md_theme_light_onPrimary,
	primaryContainer = md_theme_light_primaryContainer,
	onPrimaryContainer = md_theme_light_onPrimaryContainer,
	secondary = md_theme_light_secondary,
	onSecondary = md_theme_light_onSecondary,
	secondaryContainer = md_theme_light_secondaryContainer,
	onSecondaryContainer = md_theme_light_onSecondaryContainer,
	tertiary = md_theme_light_tertiary,
	onTertiary = md_theme_light_onTertiary,
	tertiaryContainer = md_theme_light_tertiaryContainer,
	onTertiaryContainer = md_theme_light_onTertiaryContainer,
	error = md_theme_dark_error,
	errorContainer = md_theme_dark_errorContainer,
	onError = md_theme_dark_onError,
	onErrorContainer = md_theme_dark_onErrorContainer,
	background = md_theme_dark_background,
	onBackground = md_theme_dark_onBackground,
	surface = md_theme_dark_surface,
	onSurface = md_theme_dark_onSurface,
	surfaceVariant = md_theme_dark_surfaceVariant,
	onSurfaceVariant = md_theme_dark_onSurfaceVariant,
	outline = md_theme_dark_outline,
	inverseOnSurface = md_theme_dark_inverseOnSurface,
	inverseSurface = md_theme_dark_inverseSurface,
	inversePrimary = md_theme_dark_inversePrimary,
	surfaceTint = md_theme_dark_surfaceTint,
	outlineVariant = md_theme_dark_outlineVariant,
	scrim = md_theme_dark_scrim,
)

val DefaultColors = darkColorScheme(
	primary = Color(0xFFFFD178),
	onPrimary = Color(0xFF000000),
	primaryContainer = md_theme_dark_primaryContainer,
	onPrimaryContainer = md_theme_dark_onPrimaryContainer,
	secondary = md_theme_dark_secondary,
	onSecondary = md_theme_dark_onSecondary,
	secondaryContainer = md_theme_dark_secondaryContainer,
	onSecondaryContainer = md_theme_dark_onSecondaryContainer,
	error = md_theme_dark_error,
	errorContainer = md_theme_dark_errorContainer,
	onError = md_theme_dark_onError,
	onErrorContainer = md_theme_dark_onErrorContainer,
	background = Color.Black,
	onBackground = Color.White,
	surface = Color(0xFF313131),
	onSurface = Color(0xFFFFFFFF),
	surfaceVariant = Color(0xFF474747),
	onSurfaceVariant = Color(0xFFFFFFFF),
	outline = Color(0x71FFFFFF),
	inverseOnSurface = md_theme_dark_inverseOnSurface,
	inverseSurface = md_theme_dark_inverseSurface,
	inversePrimary = md_theme_dark_inversePrimary,
	surfaceTint = md_theme_dark_surfaceTint,
	outlineVariant = md_theme_dark_outlineVariant,
	scrim = md_theme_dark_scrim,
)

@Composable
fun AppTheme(
	useDarkTheme: Boolean = isSystemInDarkTheme(),
	content: @Composable() () -> Unit
) {
	val colors = if (!useDarkTheme) {
		LightColors
	} else {
		DarkColors
	}

	MaterialTheme(
		colorScheme = DefaultColors,
		content = content
	)
}
