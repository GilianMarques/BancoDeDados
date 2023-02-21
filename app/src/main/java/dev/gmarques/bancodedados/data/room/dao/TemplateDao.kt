package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.entidades.relacoes.TemplateComCampos

@Dao
abstract class TemplateDao : BaseDao<TemplateEntidade>() {

    @Transaction
    @Query("SELECT * FROM templates")
    suspend abstract fun getTodosOsTemplates(): List<TemplateComCampos>

}