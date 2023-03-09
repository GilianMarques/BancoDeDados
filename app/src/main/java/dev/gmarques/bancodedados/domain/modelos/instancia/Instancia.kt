package dev.gmarques.bancodedados.domain.modelos.instancia

import java.util.*

class Instancia : java.io.Serializable {

    lateinit var templateUid: String

    val uid: String = UUID.randomUUID().toString()

    private val propriedades = ArrayList<Propriedade>()


    fun addPropriedade(propriedade: Propriedade) = propriedades.add(propriedade)

    fun removerPropriedade(propriedade: Propriedade) = propriedades.remove(propriedade)

    fun getPropriedades(): List<Propriedade> = Collections.unmodifiableList(propriedades)

    fun removerTodasAsPropriedades() {
        propriedades.clear()
    }
}

