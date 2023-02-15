package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.room.entidades.EntradaEntidade

@Dao
abstract class EntradaDao : BaseDao<EntradaEntidade>() {

    @Query("SELECT * FROM entradas WHERE template_uid LIKE :templateUid")
    abstract suspend  fun getCamposPorTemplate(templateUid: String): List<EntradaEntidade>

}