package dev.gmarques.bancodedados.domain.modelos.instancia

import java.util.*

class Instancia {

    lateinit var templateUid: String
    val uid: String = UUID.randomUUID().toString()
    val propriedades = ArrayList<Propriedade>()

}

