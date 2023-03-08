package dev.gmarques.bancodedados.domain.modelos.template

import androidx.test.ext.junit.runners.AndroidJUnit4
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MAXIMO_PERMITIDO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.COMPRIMENTO_MINIMO_PERMITIDO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MAIOR_VALOR_PERMITIDO
import dev.gmarques.bancodedados.domain.modelos.template.Campo.Companion.MENOR_VALOR_PERMITIDO
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CampoTest : TestCase() {

    @Test
    fun validarEntradaNumerica() {

        val campoNumerico = Campo( TipoCampo.NUMERO)
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

        val campoDeTextoQuePodeSerVazio = Campo(TipoCampo.TEXTO)
            .apply {
                comprimentoMaximo = 20
                comprimentoMinimo = 5
                podeSerVazio = true
            }


        assertTrue(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto("teste de comprimento"))
        assertTrue(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto("teste"))

        assertFalse(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto("teste de comprimento invalido"))
        assertFalse(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto("test"))

        assertTrue(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto(""))
        assertTrue(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto("     "))
        assertTrue(campoDeTextoQuePodeSerVazio.validarEntradaDeTexto(null))


        val campoDeTexto2 = Campo( TipoCampo.TEXTO)
            .apply {
                comprimentoMaximo = 20
                comprimentoMinimo = 5
            }

        assertFalse(campoDeTexto2.validarEntradaDeTexto(""))
        assertFalse(campoDeTexto2.validarEntradaDeTexto("    "))
        assertFalse(campoDeTexto2.validarEntradaDeTexto(null))

    }

    @Test
    fun validarNome() {
        Assert.assertFalse(Campo.validarNome(null))
        Assert.assertFalse(Campo.validarNome(""))
        Assert.assertTrue(Campo.validarNome("nome valido"))
    }

    @Test
    fun validarRegrasTexto() {

        Campo.validarRegrasTexto(
            COMPRIMENTO_MAXIMO_PERMITIDO + 1,
            COMPRIMENTO_MINIMO_PERMITIDO
        ).also { falharPorComprimentoLongoDeMais ->
            assertFalse(falharPorComprimentoLongoDeMais)
        }

        Campo.validarRegrasTexto(
            COMPRIMENTO_MAXIMO_PERMITIDO,
            COMPRIMENTO_MINIMO_PERMITIDO - 1
        ).also { falharPorComprimentoCurtoDeMais ->
            assertFalse(falharPorComprimentoCurtoDeMais)
        }


        Campo.validarRegrasTexto(
            COMPRIMENTO_MAXIMO_PERMITIDO + 1,
            COMPRIMENTO_MINIMO_PERMITIDO - 1
        ).also { falharPorComprimentoCurtoELongoDeMais ->
            assertFalse(falharPorComprimentoCurtoELongoDeMais)
        }


        Campo.validarRegrasTexto(
            COMPRIMENTO_MAXIMO_PERMITIDO,
            COMPRIMENTO_MINIMO_PERMITIDO
        ).also { passarPorComprimentoValido ->
            assertTrue(passarPorComprimentoValido)
        }


    }

    @Test
    fun validarRegrasNumero() {

        Campo.validarRegrasNumero(
            MAIOR_VALOR_PERMITIDO,
            MENOR_VALOR_PERMITIDO - 1
        ).also { deveFalharPorMenorQueEstarForaDosLimites ->
            assertFalse(deveFalharPorMenorQueEstarForaDosLimites)
        }


        Campo.validarRegrasNumero(
            MAIOR_VALOR_PERMITIDO + 1,
            MENOR_VALOR_PERMITIDO
        ).also { deveFalharPorMaiorQueEstarForaDosLimites ->
            assertFalse(deveFalharPorMaiorQueEstarForaDosLimites)
        }



        Campo.validarRegrasNumero(
            MAIOR_VALOR_PERMITIDO + 1,
            MENOR_VALOR_PERMITIDO - 1
        ).also { deveFalharPorMaiorEMenorQueEstaremForaDosLimites ->
            assertFalse(deveFalharPorMaiorEMenorQueEstaremForaDosLimites)
        }


        Campo.validarRegrasNumero(
            MAIOR_VALOR_PERMITIDO,
            MENOR_VALOR_PERMITIDO
        ).also { devePassarPoisAmbosOsValoresEstaoDentroDosLimites ->
            assertTrue(devePassarPoisAmbosOsValoresEstaoDentroDosLimites)
        }


    }


}