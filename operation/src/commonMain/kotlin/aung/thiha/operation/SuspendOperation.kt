package aung.thiha.operation

fun interface SuspendOperation<I, O> : suspend (I) -> Outcome<O>

fun <I, O> suspendOperation(
    mapError: (Exception) -> Outcome<O> = DefaultErrorMapper(),
    block: suspend (I) -> O
): SuspendOperation<I, O> = SuspendOperation { input ->
    try {
        Outcome.Success(block(input))
    } catch (e: Exception) {
        mapError(e)
    }
}
