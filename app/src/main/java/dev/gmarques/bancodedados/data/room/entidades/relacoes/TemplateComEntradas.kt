package dev.gmarques.bancodedados.data.room.entidades.relacoes

import androidx.room.Embedded
import androidx.room.Relation
import dev.gmarques.bancodedados.data.room.entidades.EntradaEntidade
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade

/**
 * Data class para recuperar do banco de dados objetos "Instancia" com seus propriedades em uma unica query
 * @see InstanciaDao
 */
data class TemplateComEntradas(
    @Embedded val template: TemplateEntidade,
    @Relation(parentColumn = "uid", entityColumn = "template_uid")
    val entradas: List<EntradaEntidade>,
)

