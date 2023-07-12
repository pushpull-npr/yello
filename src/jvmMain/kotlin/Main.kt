import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowState
import androidx.compose.ui.window.application
import com.russhwolf.settings.Settings
import moe.tlaster.precompose.PreComposeWindow
import presentation.main.Main
import theme.AppTheme
import java.awt.Window
import javax.swing.JFileChooser


val LocalWindow = compositionLocalOf<Window> { error("") }

fun main() = application {

	PreComposeWindow(
		onCloseRequest = { exitApplication() },
		state = WindowState(size = DpSize(width = 1440.dp, height = 960.dp)),
		onKeyEvent = {
//			println(it)
			true
		}
	) {
		CompositionLocalProvider(
			LocalWindow provides window
		) {
			AppTheme(
				useDarkTheme = true
			) {
				Surface(
					color = MaterialTheme.colorScheme.background,
					contentColor = MaterialTheme.colorScheme.onBackground,
					modifier = Modifier.fillMaxSize()
				) {
					Main()
				}
			}
		}
	}
}
