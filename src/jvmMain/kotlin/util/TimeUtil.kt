package util


class TimeUtil {
	companion object {
		fun Long.toMinSec(): String = "${(this / 60).toString().padStart(2, '0')} : ${(this % 60).toString().padStart(2, '0')}"
		fun Int.toMinSec(): String = "${(this / 60).toString().padStart(2, '0')} : ${(this % 60).toString().padStart(2, '0')}"

		fun Long.fromMilliToMinSec(): String = "${(this / 60000).toString().padStart(2, '0')} : ${((this / 1000) % 60).toString().padStart(2, '0')}"
		fun Int.fromMilliToMinSec(): String = "${(this / 60000).toString().padStart(2, '0')} : ${((this / 1000) % 60).toString().padStart(2, '0')}"

	}
}
