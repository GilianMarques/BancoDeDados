package dev.gmarques.bancodedados.data.json_serializador

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

class JacksonJsonSerializador : JsonSerializador {

    // https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
    private val jackson = jacksonObjectMapper()

    override fun <T> toJSon(objeto: T): String = jackson.writeValueAsString(objeto)

    override fun <T> fromJson(json: String, clazz: Class<T>) = jackson.readValue(json, clazz)

}