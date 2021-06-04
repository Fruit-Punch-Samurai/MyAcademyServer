package utils.sealed

import utils.Extensions.emptyIfNull


sealed interface MyResult<out T> {
    class Success<R>(val result: R) : MyResult<R>
    class Failure(val throwable: Throwable? = null, val msg: String = throwable?.localizedMessage.emptyIfNull()) :
        MyResult<Nothing>
}