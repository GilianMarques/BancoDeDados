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

    // https://www.geeksforgeeks.org/sql-using-group-by-to-count-the-number-of-rows-for-each-unique-entry-in-a-column/
    @Query("SELECT template_uid,COUNT(*) as COUNT from instancias GROUP BY template_uid")
    suspend abstract fun contarInstancias(): List<Contagem>

    // TODO: testar função que usa essa classe
    data class Contagem(val template_uid: String, val COUNT: Int)
}