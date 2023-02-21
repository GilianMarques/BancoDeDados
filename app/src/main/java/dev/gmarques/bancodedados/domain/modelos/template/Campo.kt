package dev.gmarques.bancodedados.domain.modelos.template

import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

var PODE_SER_VAZIO = false
var COMPRIMENTO_MAXIMO = 150
var COMPRIMENTO_MINIMO = 0
var MAIOR_QUE = -999_999
var MENOR_QUE = 999_999

class Campo(@Suppress("unused") val templateUid: String, var tipoCampo: TipoCampo) :
    java.io.Serializable {


    val uid: String = UUID.randomUUID().toString()
    var nome = ""
    var podeSerVazio = PODE_SER_VAZIO
    var comprimentoMaximo = COMPRIMENTO_MAXIMO
    var comprimentoMinimo = COMPRIMENTO_MINIMO
    var maiorQue = MAIOR_QUE
    var menorQue = MENOR_QUE

    fun validarEntradaNumerica(entradaUsuario: Long?): Boolean {

        if (entradaUsuario == null && !podeSerVazio) return false
        if (entradaUsuario != null && entradaUsuario <= maiorQue) return false
        if (entradaUsuario != null && entradaUsuario >= menorQue) return false

        return true
    }

    fun validarEntradaDeTexto(entradaUsuario: String?): Boolean {

        if (entradaUsuario == null || entradaUsuario.isEmpty()) {
            if (!podeSerVazio) return false
        } else {

            if (entradaUsuario.length > comprimentoMaximo) return false
            if (entradaUsuario.length < comprimentoMinimo) return false
        }

        return true
    }
}