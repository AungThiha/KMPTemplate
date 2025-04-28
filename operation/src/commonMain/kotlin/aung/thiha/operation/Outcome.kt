package aung.thiha.operation

sealed interface Outcome<D> {
    data class Failure<D>(val type: FailureType, val e: Exception) : Outcome<D>
    data class Success<D>(val data: D): Outcome<D>
}

enum class FailureType {
    NETWORK, GENERAL;
}
