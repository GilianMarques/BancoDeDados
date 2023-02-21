package dev.gmarques.bancodedados.domain.modelos.instancia

import java.util.*

class Instancia(var templateUid: String) :java.io.Serializable{

    val uid: String = UUID.randomUUID().toString()

    val propriedades = ArrayList<Propriedade>()

}

