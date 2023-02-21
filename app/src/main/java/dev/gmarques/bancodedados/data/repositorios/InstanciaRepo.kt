package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia

object InstanciaRepo {

    suspend fun addInstancia(instancia: Instancia) {
        val entidade = Mapeador.getInstanciaEntidade(instancia)
        RoomDb.getInstancia().instanciaDao().addOuAtualizar(entidade)

    }

}
