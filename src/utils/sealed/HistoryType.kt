package utils.sealed

sealed interface HistoryType {
    object Add : HistoryType
    object Delete : HistoryType
    object Modify : HistoryType
    object Other : HistoryType
}


