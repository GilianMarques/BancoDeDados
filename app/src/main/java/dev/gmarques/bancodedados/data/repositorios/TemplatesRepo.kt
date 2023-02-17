package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

object TemplatesRepo {

    suspend fun carregarTemplates(): ArrayList<Template> = withContext(IO) {
        val templates = ArrayList<Template>()
        RoomDb.getInstancia().templateDao().getTodosOsTemplates()
            .forEach {
                templates.add(Mapeador.getTemplate(it))
            }

        return@withContext templates
    }

}