package dev.gmarques.bancodedados.domain.modelos.template

import java.util.*

class Template(
    var nome: String,
) {
    val uid: String = UUID.randomUUID().toString()
    val entradas = ArrayList<Entrada>()
}

