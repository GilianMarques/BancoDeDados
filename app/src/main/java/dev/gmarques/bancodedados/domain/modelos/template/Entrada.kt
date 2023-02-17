package dev.gmarques.bancodedados.domain.modelos.template

import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

class Entrada(var templateUid: String, var tipoCampo: TipoCampo) :java.io.Serializable{
    val uid: String = UUID.randomUUID().toString()
    var nome = ""
    var podeSerVazio = false
    var comprimentoMaximo = 25
    var comprimentoMinimo = 0
    var maiorQue = -999_999
    var menorQue = 999_999


}