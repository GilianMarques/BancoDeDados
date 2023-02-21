package dev.gmarques.bancodedados.data

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import dev.gmarques.bancodedados.data.room.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.entidades.relacoes.TemplateComCampos
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.json.JSONObject

// TODO: testar as funçoes conforme forem entrando em uso 
object Mapeador {
    // https://stackabuse.com/reading-and-writing-json-in-kotlin-with-jackson/
    private val jackson = jacksonObjectMapper()

    suspend fun getInstanciaEntidade(mInstancia: Instancia): InstanciaEntidade = withContext(IO) {
        val jsonString = JSONObject(jackson.writeValueAsString(mInstancia))
            .apply { remove("propriedades") }.toString()

        jackson.readValue(jsonString)
    }

    suspend fun getPropriedadeEntidade(mPropriedade: Propriedade): PropriedadeEntidade =
        withContext(IO) {
            jackson.readValue(jackson.writeValueAsString(mPropriedade))
        }

    suspend fun getTemplateEntidade(mTemplate: Template): TemplateEntidade = withContext(IO) {

        // classe TemplateEntidade não tem a variavel campos, removo pra evitar exceptions
        val jsonString = JSONObject(jackson.writeValueAsString(mTemplate))
            .apply { remove("campos") }.toString()

        jackson.readValue(jsonString)
    }

    suspend fun getEntradaEntidade(mCampo: Campo): CampoEntidade = withContext(IO) {
        jackson.readValue(jackson.writeValueAsString(mCampo))
    }

    suspend fun getTemplate(templateComCampos: TemplateComCampos): Template = withContext(IO) {


        val template: Template =
            jackson.readValue(jackson.writeValueAsString(templateComCampos.template))

        templateComCampos.entradas.forEach {

            val entradaEntidadeString: String = jackson.writeValueAsString(it)
            val campo: Campo = jackson.readValue(entradaEntidadeString)
            template.campos.add(campo)

        }
        return@withContext template
    }


}