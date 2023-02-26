package dev.gmarques.bancodedados.data.json_serializador

interface JsonSerializador {

    fun <T> toJSon(objeto: T): String

    fun <T> fromJson(json: String, clazz: Class<T>): T
}