import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
	kotlin("multiplatform")
	id("org.jetbrains.compose")
}

group = "com.example"
version = "1.0-SNAPSHOT"

repositories {
	google()
	mavenCentral()
	maven { url = uri("https://jitpack.io") }
	maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

kotlin {
	jvm {
		withJava()
	}
	sourceSets {
		val jvmMain by getting {
			dependencies {
				implementation(compose.desktop.currentOs) {
					exclude(group = "androidx.compose.material")
					exclude(group = "org.jetbrains.compose.material")
				}
				implementation(compose.material3)
//				api(compose.foundation)
				api(compose.animation)
				api(compose.runtime)

				implementation("org.jetbrains.compose.material3:material3-desktop:1.2.1")

				implementation("com.github.racra:smooth-corner-rect-android-compose:v1.0.0")

				implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.2")

//				Viewmodel
				api("moe.tlaster:precompose:1.4.3")
				api("moe.tlaster:precompose-viewmodel:1.4.3")

				implementation("org.apache.tika:tika-core:2.8.0")
				implementation("org.apache.tika:tika-parsers-standard-package:2.8.0")
//				implementation("commons-io:commons-io:2.13.0")

//				Database
				implementation("com.squareup.sqldelight:runtime:1.5.5")

//				Storage
				implementation("com.russhwolf:multiplatform-settings-no-arg:1.0.0")

				//				vlc bridge
				implementation("uk.co.caprica:vlcj:4.8.2")

				implementation("net.jthink:jaudiotagger:3.0.1")
//				preference storage
				implementation("com.russhwolf:multiplatform-settings:1.0.0")

//				Dbus api
				implementation("com.github.hypfvieh:dbus-java-core:4.3.0")

				// https://mvnrepository.com/artifact/org.apache.logging.log4j/log4j-core
//				implementation("org.apache.logging.log4j:log4j-core:3.0.0-alpha1")
//				implementation("org.slf4j:slf4j-simple:2.0.7")

				// https://mvnrepository.com/artifact/org.openjfx/javafx-media
//				implementation("org.openjfx:javafx-media:21-ea+24")
//				implementation("org.openjfx:javafx-base:20.0.1:linux")
//				implementation("org.openjfx:javafx-media:20.0.1:linux")
//				implementation("org.openjfx:javafx-graphics:20.0.1:linux")
			}
		}
		val jvmTest by getting {
			dependencies {
				implementation("org.jetbrains.kotlin:kotlin-test:1.9.0")
			}
		}
	}
}


compose.desktop {
	application {
		mainClass = "MainKt"
		nativeDistributions {
			targetFormats(TargetFormat.AppImage)
			packageName = "Yello"
			packageVersion = "1.0.0"

			modules("java.sql")
		}
	}
}


