package dev.gmarques.bancodedados.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.gmarques.bancodedados.data.json_serializador.JacksonJsonSerializador
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class MapeadorTest : TestCase() {

    private val mapeador = Mapeador(JacksonJsonSerializador())

    @Test
    fun getInstanciaEntidade() = runBlocking {
        /*O teste passa se  não houver excepions*/

        val instancia = Instancia()
        val propriedade =
            Propriedade( TipoCampo.BOOLEANO).apply { nome = "propriedade de teste 1" }
        instancia.propriedades.add(propriedade)

        @Suppress("UNUSED_VARIABLE") val entidade = mapeador.getInstanciaEntidade(instancia)
    }

    @Test
    fun getPropriedadeEntidade() {
    }

    @Test
    fun getTemplateEntidade() = runBlocking {

        /*O teste passa se  não houver excepions*/

        val template = Template().apply { nome="Teste template" }
        val campo = Campo(TipoCampo.BOOLEANO).apply { nome = "campo de teste 1" }
        template.addCampo(campo)

        @Suppress("UNUSED_VARIABLE") val entidade = mapeador.getTemplateEntidade(template)

    }

    @Test
    fun getEntradaEntidade() {
    }

    @Test
    fun getTemplate() {
    }
}