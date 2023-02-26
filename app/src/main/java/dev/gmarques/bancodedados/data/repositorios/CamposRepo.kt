package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CamposRepo @Inject constructor(
    private val mapeador: Mapeador,
    private val campoDao: CampoDao,
) {

    suspend fun addOuAtualizar(campo: Campo, template: Template) {

        campo.templateUid = template.uid

        val campoEntidade = mapeador.getCampoEntidade(campo)
        campoDao.addOuAtualizar(campoEntidade)
    }

}