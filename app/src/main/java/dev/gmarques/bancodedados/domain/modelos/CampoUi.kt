package dev.gmarques.bancodedados.domain.modelos

import dev.gmarques.bancodedados.domain.TipoCampo
import java.util.*

class CampoUi {
    val uid: String = UUID.randomUUID().toString()
    var nome = ""
    var templateUid = ""
    lateinit var tipoCampo: TipoCampo
    var podeSerVazio = false
    var comprimentoMaximo = 25
    var comprimentoMinimo = 0
    var maiorQue = -999_999
    var menorQue = 999_999


}
