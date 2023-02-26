package dev.gmarques.bancodedados.domain._interfaces

interface JsonSerializador {

    fun <T> toJSon(objeto: T): String

    fun <T> fromJson(json: String, clazz: Class<T>): T
}