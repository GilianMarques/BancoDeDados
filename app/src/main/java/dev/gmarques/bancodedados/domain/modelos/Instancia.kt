package dev.gmarques.bancodedados.domain.modelos

import java.util.*

class Instancia {

    lateinit var templateUid: String

    val uid: String = UUID.randomUUID().toString()

}