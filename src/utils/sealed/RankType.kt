package utils.sealed

sealed interface RankType {
    object Guest : RankType
    object Normal : RankType
    object Admin : RankType
}