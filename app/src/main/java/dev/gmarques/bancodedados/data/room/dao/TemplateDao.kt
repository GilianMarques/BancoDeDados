package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.entidades.CampoUiEntidade
import dev.gmarques.bancodedados.data.entidades.TemplateEntidade

@Dao
abstract class TemplateDao : BaseDao<TemplateEntidade>() {

    @Query(
        "SELECT * FROM templates JOIN campos_ui ON templates.uid = campos_ui.template_uid"
    )
    abstract suspend fun getTodosOsTemplates(): Map<TemplateEntidade, List<CampoUiEntidade>>
}