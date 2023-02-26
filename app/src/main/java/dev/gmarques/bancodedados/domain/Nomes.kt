package dev.gmarques.bancodedados.domain

import java.util.*

class Nomes {
    companion object {
        fun adequarNome(nome: String): String {
            var nomeAdequado = nome
            nomeAdequado = Regex("""[#%&{}<>*\\?/$!'":@+`|=]""").replace(nomeAdequado, "")
            nomeAdequado = Regex("[ ]+").replace(nomeAdequado, " ")
            nomeAdequado = nomeAdequado.trim()
            nomeAdequado = nomeAdequado.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            }

            return nomeAdequado
        }
    }
}