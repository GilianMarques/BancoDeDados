package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.entidades.relacoes.InstanciaComPropriedades

@Dao
abstract class InstanciaDao : BaseDao<InstanciaEntidade>() {

    @Transaction
    @Query("SELECT * FROM instancias WHERE template_uid LIKE :templateUid")
    suspend abstract fun getInstanciasComCampos(templateUid: String): List<InstanciaComPropriedades>

}