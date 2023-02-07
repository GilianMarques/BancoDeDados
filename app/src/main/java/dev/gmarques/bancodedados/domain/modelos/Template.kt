package dev.gmarques.bancodedados.domain.modelos

import java.util.UUID

class Template {

    val uid: String = UUID.randomUUID().toString()
    var nome = ""
}