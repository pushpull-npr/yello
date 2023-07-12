package util


class SortUtil {
	companion object {
		enum class SortBy(val visibleName : String) {
			TrackNumber("Track number"),
			Name("File name"),
			TrackLength("Track length")
		}

		enum class SortOrder {
			Ascending,
			Descending,
		}
	}
}
