package com.teddy.sweatcontacts.common.view

/**
 * A generic class that holds a value with its loading status.
 * */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(status: Status, data: T?): Resource<T> {
            return Resource(status, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }

        fun <T> loadingMore(data: T?): Resource<T> {
            return Resource(Status.LOADING_MORE, data, null)
        }
    }
}

enum class Status {
    LOADING, LOADING_MORE, SUCCESS, SUCCESS_MORE, ERROR
}