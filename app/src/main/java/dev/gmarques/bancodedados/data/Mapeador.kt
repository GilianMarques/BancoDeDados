package dev.gmarques.bancodedados.data

import dev.gmarques.bancodedados.domain._interfaces.JsonSerializador
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
import javax.inject.Inject
import javax.inject.Singleton


// TODO: testar as funçoes conforme forem entrando em uso
@Singleton
class Mapeador @Inject constructor(val jsonSerializador: JsonSerializador) {

    suspend fun getInstanciaEntidade(mInstancia: Instancia): InstanciaEntidade = withContext(IO) {
        val jsonString =
            JSONObject(jsonSerializador.toJSon(mInstancia)).apply { remove("propriedades") }
                .toString()

        jsonSerializador.fromJson(jsonString, InstanciaEntidade::class.java)
    }

    suspend fun getPropriedadeEntidade(mPropriedade: Propriedade): PropriedadeEntidade =
        withContext(IO) {
            jsonSerializador.fromJson(
                jsonSerializador.toJSon(mPropriedade),
                PropriedadeEntidade::class.java
            )
        }

    suspend fun getTemplateEntidade(mTemplate: Template): TemplateEntidade = withContext(IO) {

        // classe TemplateEntidade não tem a variavel campos, removo pra evitar exceptions
        val jsonString = JSONObject(jsonSerializador.toJSon(mTemplate))
            .apply { remove("campos") }
            .toString()

        jsonSerializador.fromJson(jsonString, TemplateEntidade::class.java)
    }

    suspend fun getCampoEntidade(mCampo: Campo): CampoEntidade = withContext(IO) {
        jsonSerializador.fromJson(jsonSerializador.toJSon(mCampo), CampoEntidade::class.java)
    }

    suspend fun getTemplate(templateComCampos: TemplateComCampos): Template = withContext(IO) {


        val template: Template =
            jsonSerializador.fromJson(
                jsonSerializador.toJSon(templateComCampos.template),
                Template::class.java
            )

        templateComCampos.entradas.forEach {

            val entradaEntidadeString: String = jsonSerializador.toJSon(it)
            val campo: Campo =
                jsonSerializador.fromJson(entradaEntidadeString, Campo::class.java)
            template.addCampo(campo)

        }
        return@withContext template
    }


}