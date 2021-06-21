package utils.sealed

sealed interface EntityType {
    val value: String

    object Student : EntityType {
        override val value: String = "student"
    }

    object Teacher : EntityType {
        override val value: String = "teacher"
    }

    object Payment : EntityType {
        override val value: String = "payment"
    }

    object Other : EntityType {
        override val value: String = "other"
    }
}


