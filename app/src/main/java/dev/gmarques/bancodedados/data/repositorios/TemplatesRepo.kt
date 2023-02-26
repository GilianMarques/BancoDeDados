package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.data.room.dao.TemplateDao
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TemplatesRepo @Inject constructor() {

    @Inject
    lateinit var mapeador: Mapeador
    @Inject
    lateinit var templateDao: TemplateDao

    suspend fun carregarTemplates(): ArrayList<Template> = withContext(IO) {
        val templates = ArrayList<Template>()
        templateDao.getTodosOsTemplates()
            .forEach {
                templates.add(mapeador.getTemplate(it))
            }

        return@withContext templates
    }

}