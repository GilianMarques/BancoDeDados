package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.room.entidades.CampoEntidade

@Dao
abstract class CampoDao : BaseDao<CampoEntidade>() {

    @Query("SELECT * FROM campos WHERE template_uid LIKE :templateUid")
    abstract suspend  fun getCamposPorTemplate(templateUid: String): List<CampoEntidade>

}