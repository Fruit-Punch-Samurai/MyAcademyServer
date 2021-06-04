package utils.sealed

sealed interface HistoryType {
    object Add : HistoryType
    object Delete : HistoryType
    object Update : HistoryType
    object Other : HistoryType
}


