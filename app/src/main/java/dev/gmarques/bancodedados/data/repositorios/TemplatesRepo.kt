package dev.gmarques.bancodedados.data.repositorios

import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.template.Template

object TemplatesRepo {

    suspend fun carregarTemplates(): ArrayList<Template> {
        val templates = ArrayList<Template>()
        RoomDb.getInstancia().templateDao().getTodosOsTemplates()
            .forEach {
                // TODO: converter e retornar templates
                // TODO: depois terminar de popular interface
                templates.add(Mapeador.dominioDe(it))
            }

    }

}