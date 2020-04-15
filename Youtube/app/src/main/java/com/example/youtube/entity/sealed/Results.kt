package com.example.youtube.entity.sealed

sealed class Results<out T:Any>{
   data class Success<out T:Any>(val value: T):Results<T>()
   data class Error(val message: String, val cause: Exception? = null) : Results<Nothing>()
}