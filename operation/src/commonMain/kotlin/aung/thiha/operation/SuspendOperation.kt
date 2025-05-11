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

suspend inline fun <reified O> SuspendOperation<Unit, O>.getOrNull(): O? {
    return invoke(Unit).getOrNull()
}

suspend inline fun <I, reified O> SuspendOperation<I, O>.getOrNull(input: I): O? {
    return invoke(input).getOrNull()
}
