package dev.gmarques.bancodedados.domain.modelos.template

import java.util.*

class Template(var nome: String) : java.io.Serializable {
    val uid: String = UUID.randomUUID().toString()

    val campos = ArrayList<Campo>()


}

