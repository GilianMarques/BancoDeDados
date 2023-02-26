package dev.gmarques.bancodedados.domain.modelos.template

import dev.gmarques.bancodedados.domain.Nomes
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

class Campo(@Suppress("unused") val templateUid: String, var tipoCampo: TipoCampo) :
    java.io.Serializable {

    val uid: String = UUID.randomUUID().toString()
    var nome = ""
        set(value) {
            field = Nomes.adequarNome(value)
        }
    var podeSerVazio = PODE_SER_VAZIO_PADRAO
    var comprimentoMaximo = COMPRIMENTO_MAXIMO_PADRAO
    var comprimentoMinimo = COMPRIMENTO_MINIMO_PADRAO
    var maiorQue = MAIOR_QUE_PADRAO
    var menorQue = MENOR_QUE_PADRAO

    /**
     * Valida o valor que o usuario esta tentando inserir no campo
     * */
    fun validarEntradaNumerica(entradaUsuario: Long?): Boolean {

        if (entradaUsuario == null && !podeSerVazio) return false
        if (entradaUsuario != null && entradaUsuario <= maiorQue) return false
        if (entradaUsuario != null && entradaUsuario >= menorQue) return false

        return true
    }

    /**
     * Valida o valor que o usuario esta tentando inserir no campo
     * */
    fun validarEntradaDeTexto(entradaUsuario: String?): Boolean {

        if (entradaUsuario == null || entradaUsuario.isEmpty()) {
            if (!podeSerVazio) return false
        } else {

            if (entradaUsuario.length > comprimentoMaximo) return false
            if (entradaUsuario.length < comprimentoMinimo) return false
        }

        return true
    }

    companion object {

        // regras de negocio
        const val COMPRIMENTO_MAXIMO_PERMITIDO = 1500
        const val COMPRIMENTO_MINIMO_PERMITIDO = 0
        const val MENOR_VALOR_PERMITIDO = -999_999_999
        const val MAIOR_VALOR_PERMITIDO = 999_999_999

        // valores padrao
        val PODE_SER_VAZIO_PADRAO = false
        val COMPRIMENTO_MAXIMO_PADRAO = 150
        val COMPRIMENTO_MINIMO_PADRAO = 0
        val MAIOR_QUE_PADRAO = -999_999
        val MENOR_QUE_PADRAO = 999_999

        /**
         * Valida o nome do campo
         * */
        fun validarNome(nome: String?): Boolean = nome?.isNotEmpty() == true

        /**
         * Valida as regras do campo de texto
         * */
        fun validarRegrasTexto(compMaximo: Int?, compMinimo: Int?): Boolean {

            val limites = COMPRIMENTO_MINIMO_PERMITIDO..COMPRIMENTO_MAXIMO_PERMITIDO

            return limites.contains((compMinimo ?: COMPRIMENTO_MINIMO_PADRAO))
                    &&
                    limites.contains((compMaximo ?: COMPRIMENTO_MAXIMO_PADRAO))

        }

        /**
         * Valida as regras do campo numerico
         * */
        fun validarRegrasNumero(maiorQue: Int?, menorQue: Int?): Boolean {

            val limites = MENOR_VALOR_PERMITIDO..MAIOR_VALOR_PERMITIDO

            return limites.contains((menorQue ?: MENOR_QUE_PADRAO))
                    &&
                    limites.contains((maiorQue ?: MAIOR_QUE_PADRAO))

        }

    }
}