package com.josedev.toforgetntme.utils

sealed class Resource<T> (val data: T? = null, val message: String? = ""){
    class Success<T>(data: T?): Resource<T>(data)
    class Error<T>(data: T? = null,message: String?): Resource<T>(data,message)
    class Loading<T>(): Resource<T>()
}
