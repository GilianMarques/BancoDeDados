package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.entidades.CampoUiEntidade

@Dao
abstract class CampoUiDao : BaseDao<CampoUiEntidade>() {

    @Query("SELECT * FROM campos_ui WHERE template_uid LIKE :templateUid")
    abstract suspend  fun getCamposPorTemplate(templateUid: String): List<CampoUiEntidade>

}