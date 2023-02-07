package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade

@Dao
abstract class InstanciaDao : BaseDao<InstanciaEntidade>() {

    @Query(
        "SELECT * FROM instancias JOIN campos ON instancias.uid = campos.instancia_uid WHERE template_uid LIKE :templateUid"
    )
    abstract  suspend fun getTodasInstancias(templateUid: String): Map<InstanciaEntidade, List<CampoEntidade>>
}