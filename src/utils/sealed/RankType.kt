package utils.sealed

sealed interface RankType {
    val value: String

    object Guest : RankType {
        override val value: String = "guest"
    }

    object Normal : RankType {
        override val value: String = "normal"
    }

    object Admin : RankType {
        override val value: String = "admin"
    }

}