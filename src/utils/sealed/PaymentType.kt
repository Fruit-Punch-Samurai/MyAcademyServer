package utils.sealed

sealed interface PaymentType {
    val value: String

    object Incoming : PaymentType {
        override val value: String = "incoming"
    }

    object Outgoing : PaymentType {
        override val value: String = "outgoing"
    }

    object Other : PaymentType {
        override val value: String = "other"
    }
}


