package dev.gmarques.bancodedados.data.entidades.relacoes

import androidx.room.Embedded
import androidx.room.Relation
import dev.gmarques.bancodedados.data.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade

/**
 * Data class para recuperar do banco de dados objetos "Instancia" com seus campos em uma unica query
 * @see InstanciaDao
 */
data class InstanciaComCampos(
    @Embedded val instancia: InstanciaEntidade,
    @Relation(parentColumn = "uid", entityColumn = "instancia_uid")
    val campos: List<CampoEntidade>,
)

