package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade

object PropriedadeRepo {

    suspend fun addPropriedade(propriedade: Propriedade) {
        val entidade = Mapeador.getPropriedadeEntidade(propriedade)
        RoomDb.getInstancia().propriedadeDao().addOuAtualizar(entidade)
    }

}
