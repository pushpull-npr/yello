package data

import com.russhwolf.settings.Settings
import java.io.Serializable
import java.util.prefs.Preferences


val settings: Settings = Settings()


data class PersistentStore(
	val filePath : String
) : Serializable
