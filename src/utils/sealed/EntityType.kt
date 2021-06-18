package utils.sealed

sealed interface EntityType {
    object Student : EntityType
    object Teacher : EntityType
    object Other : EntityType
}


