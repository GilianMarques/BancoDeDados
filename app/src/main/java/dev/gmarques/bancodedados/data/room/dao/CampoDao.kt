package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.entidades.CampoEntidade

@Dao
abstract class CampoDao : BaseDao<CampoEntidade>() {


    @Query("SELECT * FROM campos WHERE instancia_uid LIKE :instanciaUid")
    abstract suspend fun getCampos(instanciaUid: String): List<CampoEntidade>


}