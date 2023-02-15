package dev.gmarques.bancodedados.data

import com.google.gson.Gson
import dev.gmarques.bancodedados.data.room.entidades.EntradaEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.entidades.relacoes.TemplateComEntradas
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template

// TODO: criar teste pra essa classe, preciso saber se o objeto segue o mesmo so converter e desconverter em outro
object Mapeador {

    private val gson = Gson()

    fun getInstanciaEntidade(mInstancia: Instancia): InstanciaEntidade =
        gson.fromJson(gson.toJson(mInstancia), InstanciaEntidade::class.java)


    fun getPropriedadeEntidade(mPropriedade: Propriedade): PropriedadeEntidade =
        gson.fromJson(gson.toJson(mPropriedade), PropriedadeEntidade::class.java)


    fun getTemplateEntidade(mTemplate: Template): TemplateEntidade =
        gson.fromJson(gson.toJson(mTemplate), TemplateEntidade::class.java)


    fun getEntradaEntidade(mEntrada: Entrada): EntradaEntidade =
        gson.fromJson(gson.toJson(mEntrada), EntradaEntidade::class.java)

    fun getTemplate(templateComEntradas: TemplateComEntradas): Template {

        val template = gson.fromJson(gson.toJson(templateComEntradas.template), Template::class.java)

        templateComEntradas.entradas.forEach {
            val entrada = gson.fromJson(gson.toJson(it), Entrada::class.java)
            template.entradas.add(entrada)
        }
        return template
    }


}