package dev.gmarques.bancodedados.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.gmarques.bancodedados.data.room.entidades.EntradaEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.entidades.relacoes.TemplateComEntradas
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext

// TODO: criar teste pra essa classe, preciso saber se o objeto segue o mesmo so converter e desconverter em outro
object Mapeador {
        // https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
    val jackson = jacksonObjectMapper()

    suspend fun getInstanciaEntidade(mInstancia: Instancia): InstanciaEntidade = withContext(IO) {
        jackson.readValue(jackson.writeValueAsString(mInstancia))
    }

    suspend fun getPropriedadeEntidade(mPropriedade: Propriedade): PropriedadeEntidade =
        withContext(IO) {
            jackson.readValue(jackson.writeValueAsString(mPropriedade))
        }

    suspend fun getTemplateEntidade(mTemplate: Template): TemplateEntidade = withContext(IO) {
        jackson.readValue(jackson.writeValueAsString(mTemplate))
    }

    suspend fun getEntradaEntidade(mEntrada: Entrada): EntradaEntidade = withContext(IO) {
        jackson.readValue(jackson.writeValueAsString(mEntrada))
    }


    suspend fun getTemplate(templateComEntradas: TemplateComEntradas): Template = withContext(IO) {


        val template: Template =
            jackson.readValue(jackson.writeValueAsString(templateComEntradas.template))

        templateComEntradas.entradas.forEach {

            val entradaEntidadeString: String = jackson.writeValueAsString(it)
            val entrada: Entrada = jackson.readValue(entradaEntidadeString)
            template.entradas.add(entrada)

        }
        return@withContext template
    }


}