package dev.gmarques.bancodedados.domain.modelos.instancia

import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

class Propriedade():java.io.Serializable{

    constructor(instanciaUid: String, tipo: TipoCampo) : this() {
        this.instanciaUid = instanciaUid
        this.tipoCampo = tipo
    }

    lateinit var instanciaUid: String

    val uid: String = UUID.randomUUID().toString()
    var nome = ""

    lateinit var tipoCampo: TipoCampo

    var valorString: String = ""
    var valorDouble = 0.0
    var valorBoolean = false

}