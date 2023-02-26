package dev.gmarques.bancodedados.domain.modelos.template

import dev.gmarques.bancodedados.domain.Nomes
import java.util.*
import kotlin.collections.ArrayList

class Template : java.io.Serializable {

    var nome: String = ""
        set(value) {
            field = Nomes.adequarNome(value)
        }

    val uid: String = UUID.randomUUID().toString()

    private val campos: ArrayList<Campo> = ArrayList()


    fun addCampo(campo: Campo) = campos.add(campo)

    fun removerCampo(campo: Campo) = campos.remove(campo)

    fun getCampos() = Collections.unmodifiableList(campos)
}

