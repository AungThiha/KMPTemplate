package aung.thiha.operation

fun interface Operation<I, O> : (I) -> Outcome<O>

fun <I, O> operation(
    mapError: (Exception) -> Outcome<O> = DefaultErrorMapper(),
    block: (I) -> O
): Operation<I, O> = Operation { input ->
    try {
        Outcome.Success(block(input))
    } catch (e: Exception) {
        mapError(e)
    }
}
