package dev.gmarques.bancodedados.domain.modelos.template

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import org.junit.Assert.*

import junit.framework.TestCase

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
class CampoTest : TestCase() {

    @Test
    fun validarEntradaNumerica() {

        val campoNumerico = Campo("template_uid_123", TipoCampo.NUMERO)
            .apply {
                maiorQue = 0
                menorQue = 101
                podeSerVazio = true
            }

        assertTrue(campoNumerico.validarEntradaNumerica(1))
        assertTrue(campoNumerico.validarEntradaNumerica(100))

        assertFalse(campoNumerico.validarEntradaNumerica(0))
        assertFalse(campoNumerico.validarEntradaNumerica(101))

        assertTrue(campoNumerico.validarEntradaNumerica(null))
    }

    @Test
    fun validarEntradaDeTexto() {

        val campoDeTexto = Campo("tempalte_uid_345", TipoCampo.TEXTO)
            .apply {
                comprimentoMaximo = 20
                comprimentoMinimo = 5
                podeSerVazio = true
            }


        assertTrue(campoDeTexto.validarEntradaDeTexto("teste de comprimento"))
        assertTrue(campoDeTexto.validarEntradaDeTexto("teste"))

        assertFalse(campoDeTexto.validarEntradaDeTexto("teste de comprimento invalido"))
        assertFalse(campoDeTexto.validarEntradaDeTexto("test"))

        assertTrue(campoDeTexto.validarEntradaDeTexto(""))
        assertTrue(campoDeTexto.validarEntradaDeTexto(null))


        val campoDeTexto2 = Campo("tempalte_uid_345", TipoCampo.TEXTO)
            .apply {
                comprimentoMaximo = 20
                comprimentoMinimo = 5
            }

        assertFalse(campoDeTexto2.validarEntradaDeTexto(""))
        assertFalse(campoDeTexto2.validarEntradaDeTexto(null))

    }
}