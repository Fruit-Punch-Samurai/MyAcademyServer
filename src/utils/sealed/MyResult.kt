package utils.sealed

import utils.Extensions.emptyIfNull


sealed interface MyResult<out T> {
    class Success<R>(val value: R) : MyResult<R>
    class Failure(
        private val throwable: Throwable? = null,
        msg: String = throwable?.localizedMessage.emptyIfNull()
    ) :
        MyResult<Nothing>
}