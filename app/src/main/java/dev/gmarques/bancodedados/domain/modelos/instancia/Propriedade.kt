package dev.gmarques.bancodedados.domain.modelos.instancia

import dev.gmarques.bancodedados.domain.Nomes
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

class Propriedade():java.io.Serializable{

    constructor( tipo: TipoCampo) : this() {
        this.tipoCampo = tipo
    }

    lateinit var instanciaUid: String

    val uid: String = UUID.randomUUID().toString()
    var nome = ""
        set(value) {
            field = Nomes.adequarNome(value)
        }
    lateinit var tipoCampo: TipoCampo

    var valorString: String = ""
    var valorDouble = 0.0
    var valorBoolean = false

}