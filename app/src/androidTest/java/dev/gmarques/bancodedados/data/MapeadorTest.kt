package dev.gmarques.bancodedados.data

import androidx.test.ext.junit.runners.AndroidJUnit4
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

@RunWith(AndroidJUnit4::class)
class MapeadorTest : TestCase() {

    @Test
    fun getInstanciaEntidade()  = runBlocking {
        /*O teste passa se  não houver excepions*/

        val instancia = Instancia("template_uid_123")
        val propriedade = Propriedade(instancia.uid, TipoCampo.REAL).apply { nome = "propriedade de teste 1" }
        instancia.propriedades.add(propriedade)

        val entidade = Mapeador.getInstanciaEntidade(instancia)
    }

    @Test
    fun getPropriedadeEntidade() {
    }

    @Test
    fun getTemplateEntidade() = runBlocking {

        /*O teste passa se  não houver excepions*/

        val template = Template("Teste template")
        val campo = Campo(template.uid, TipoCampo.REAL).apply { nome = "campo de teste 1" }
        template.campos.add(campo)

        val entidade = Mapeador.getTemplateEntidade(template)

    }

    @Test
    fun getEntradaEntidade() {
    }

    @Test
    fun getTemplate() {
    }
}