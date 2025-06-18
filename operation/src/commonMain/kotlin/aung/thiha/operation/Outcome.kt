package aung.thiha.operation

sealed interface Outcome<D> {
    data class Failure<D>(val type: FailureType, val e: Exception) : Outcome<D>
    data class Success<D>(val data: D): Outcome<D>
}

enum class FailureType {
    NETWORK, GENERAL;
}

inline fun <reified T> Outcome<T>.getOrNull(): T? {
    return (this as? Outcome.Success)?.data
}

inline fun <reified T> Outcome<T>.rethrowIfFailure() {
    (this as? Outcome.Failure)?.e?.let { throw it }
}
