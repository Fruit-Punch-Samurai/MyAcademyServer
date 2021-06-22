package utils.sealed

sealed interface HistoryType {
    val value: String

    object Add : HistoryType {
        override val value: String = "add"
    }

    object Delete : HistoryType {
        override val value: String = "delete"
    }

    object Modify : HistoryType {
        override val value: String = "modify"
    }

    object Other : HistoryType {
        override val value: String = "other"
    }
}


