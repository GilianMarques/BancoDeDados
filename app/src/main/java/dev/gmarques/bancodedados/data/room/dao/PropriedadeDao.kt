package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Dao
import androidx.room.Query
import dev.gmarques.bancodedados.data.entidades.PropriedadeEntidade

@Dao
abstract class PropriedadeDao : BaseDao<PropriedadeEntidade>() {


    @Query("SELECT * FROM propriedades WHERE instancia_uid LIKE :instanciaUid")
    abstract suspend fun getCampos(instanciaUid: String): List<PropriedadeEntidade>


}