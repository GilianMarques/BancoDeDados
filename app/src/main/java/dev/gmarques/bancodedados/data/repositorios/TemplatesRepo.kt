package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.dao.TemplateDao
import dev.gmarques.bancodedados.data.room.entidades.relacoes.TemplateComCampos
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplatesRepo @Inject constructor(
    private val mapeador: Mapeador,
    private val templateDao: TemplateDao,
) {


    suspend fun carregarTemplates(): ArrayList<Template> = withContext(IO) {
        return@withContext templateDao
            .getTodosOsTemplates()
            .map<TemplateComCampos, Template> { mapeador.getTemplate(it) }
    } as ArrayList<Template>

    suspend fun addOuAtualizar(template: Template) {
        val templateEntidade = mapeador.getTemplateEntidade(template)
        templateDao.addOuAtualizar(templateEntidade)
    }

}