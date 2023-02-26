package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.data.room.dao.PropriedadeDao
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropriedadeRepo @Inject constructor() {

    @Inject
    lateinit var mapeador: Mapeador

    @Inject
    lateinit var propriedadeDao: PropriedadeDao

    suspend fun addPropriedade(propriedade: Propriedade) {
        val entidade = mapeador.getPropriedadeEntidade(propriedade)
        propriedadeDao.addOuAtualizar(entidade)
    }

}
