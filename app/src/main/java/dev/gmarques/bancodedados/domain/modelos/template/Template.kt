package dev.gmarques.bancodedados.domain.modelos.template

import java.util.*
import kotlin.collections.ArrayList

class Template(var nome: String) : java.io.Serializable {

    val uid: String = UUID.randomUUID().toString()

    private val campos: ArrayList<Campo> = ArrayList()


    fun addCampo(campo: Campo) = campos.add(campo)

    fun removerCampo(campo: Campo) = campos.remove(campo)

    fun getCampos() = Collections.unmodifiableList(campos)
}

