package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.data.room.dao.PropriedadeDao
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PropriedadeRepo @Inject constructor(
    private val mapeador: Mapeador,
    private val propriedadeDao: PropriedadeDao,
) {
    suspend fun addPropriedade(propriedade: Propriedade, instancia: Instancia) {
        propriedade.instanciaUid = instancia.uid
        val propriedadeEntidade = mapeador.getPropriedadeEntidade(propriedade)
        propriedadeDao.addOuAtualizar(propriedadeEntidade)
    }

}
