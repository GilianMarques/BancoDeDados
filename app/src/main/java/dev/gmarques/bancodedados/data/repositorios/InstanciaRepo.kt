package dev.gmarques.bancodedados.data.repositorios

import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.template.Template
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class InstanciaRepo @Inject constructor(
    private val mapeador: Mapeador,
    private val instanciaDao: InstanciaDao,
) {

    @InstallIn(SingletonComponent::class)
    @EntryPoint
    interface InstanciaRepoEntryPoint {
        fun getRepoInstancia(): InstanciaRepo;
    }

    @Suppress("unused")
            /**
             * Usada para obter uma instancia dessa classe via Hilt EntryPointAccessors
             * */
    fun getRepoInstancia(): InstanciaRepo {
        return this
    }


    suspend fun addInstancia(instancia: Instancia, template: Template) {
        instancia.templateUid = template.uid
        val entidade = mapeador.getInstanciaEntidade(instancia)
        instanciaDao.addOuAtualizar(entidade)

    }

    suspend fun contarInstancias(uid: String): Int = instanciaDao.contarInstancias(uid)

}
