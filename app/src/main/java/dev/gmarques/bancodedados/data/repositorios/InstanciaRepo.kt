package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanciaRepo @Inject constructor() {

    @Inject
    lateinit var mapeador: Mapeador

    @Inject
    lateinit var instanciaDao: InstanciaDao

    suspend fun addInstancia(instancia: Instancia) {
        val entidade = mapeador.getInstanciaEntidade(instancia)
        instanciaDao.addOuAtualizar(entidade)

    }

    suspend fun contarInstancias(uid: String): Int = instanciaDao.contarInstancias(uid)

}
