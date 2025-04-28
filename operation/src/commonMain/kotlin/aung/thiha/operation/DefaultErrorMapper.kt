package aung.thiha.operation

import io.ktor.utils.io.errors.IOException

class DefaultErrorMapper<O> : (Exception) -> Outcome<O> {
    override fun invoke(exception: Exception): Outcome<O> {
        return when (exception) {
            is IOException -> Outcome.Failure(FailureType.NETWORK, exception)
            else -> Outcome.Failure(FailureType.GENERAL, exception)
        }
    }
}
