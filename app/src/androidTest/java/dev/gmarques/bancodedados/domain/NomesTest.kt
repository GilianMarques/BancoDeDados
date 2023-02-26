package dev.gmarques.bancodedados.domain

import android.util.Log
import org.junit.Assert.*

import junit.framework.TestCase
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class NomesTest : TestCase() {

    @Test
    fun adequarNome() {

        val nomeInvalido = " #teste%  de &nome {válido}\\<>*?/\$!'\":@+`|=  "
        val nomeValido = Nomes.adequarNome(nomeInvalido)
        Log.d("USUK", "NomesTest.adequarNome: '$nomeValido'")
        assertEquals("Teste de nome válido", nomeValido)
    }
}