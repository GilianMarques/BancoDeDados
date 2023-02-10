package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.gmarques.bancodedados.data.entidades.EntradaEntidade
import dev.gmarques.bancodedados.data.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.entidades.relacoes.InstanciaComPropriedades
import dev.gmarques.bancodedados.data.entidades.relacoes.TemplateComEntradas

@Dao
abstract class TemplateDao : BaseDao<TemplateEntidade>() {

    @Transaction
    @Query("SELECT * FROM templates")
    suspend abstract fun getTodosOsTemplates(): List<TemplateComEntradas>

}