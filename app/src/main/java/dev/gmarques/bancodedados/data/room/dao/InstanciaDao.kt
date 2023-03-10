package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.relacoes.InstanciaComPropriedades

@Dao
abstract class InstanciaDao : BaseDao<InstanciaEntidade>() {

    @Transaction
    @Query("SELECT * FROM instancias WHERE template_uid LIKE :templateUid")
    suspend abstract fun getInstanciasComCampos(templateUid: String): List<InstanciaComPropriedades>


    @Query("SELECT COUNT(*) from instancias WHERE template_uid LIKE :templateUid")
     suspend abstract fun contarInstancias(templateUid: String): Int

}