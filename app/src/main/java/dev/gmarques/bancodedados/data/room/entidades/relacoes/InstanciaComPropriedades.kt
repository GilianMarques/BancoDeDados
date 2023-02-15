package dev.gmarques.bancodedados.data.room.entidades.relacoes

import androidx.room.Embedded
import androidx.room.Relation
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade

/**
 * Data class para recuperar do banco de dados objetos "Instancia" com seus propriedades em uma unica query
 * @see InstanciaDao
 */
data class InstanciaComPropriedades(
    @Embedded val instancia: InstanciaEntidade,
    @Relation(parentColumn = "uid", entityColumn = "instancia_uid")
    val propriedades: List<PropriedadeEntidade>,
)

