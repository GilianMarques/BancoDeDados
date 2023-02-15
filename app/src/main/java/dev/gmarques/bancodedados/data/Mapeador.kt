package dev.gmarques.bancodedados.data

import com.google.gson.Gson
import dev.gmarques.bancodedados.data.room.entidades.EntradaEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template

object Mapeador {

    private val gson = Gson()

    fun entidadeDe(mInstancia: Instancia): InstanciaEntidade =
        gson.fromJson(gson.toJson(mInstancia), InstanciaEntidade::class.java)


    fun entidadeDe(mPropriedade: Propriedade): PropriedadeEntidade =
        gson.fromJson(gson.toJson(mPropriedade), PropriedadeEntidade::class.java)


    fun entidadeDe(mTemplate: Template): TemplateEntidade =
        gson.fromJson(gson.toJson(mTemplate), TemplateEntidade::class.java)


    fun entidadeDe(mEntrada: Entrada): EntradaEntidade =
        gson.fromJson(gson.toJson(mEntrada), EntradaEntidade::class.java)


}