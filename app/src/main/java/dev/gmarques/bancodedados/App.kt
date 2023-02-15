package dev.gmarques.bancodedados

import android.app.Application
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.runBlocking

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        get = this
        RoomDb.getInstancia()
        popularRoom()
    }

    private fun popularRoom() {
        runBlocking {
            if (RoomDb.getInstancia().templateDao().getTodosOsTemplates().isNotEmpty()) return@runBlocking


            val musica = Template("Musica")
            val artista = Entrada(musica.uid, TipoCampo.TEXTO).apply {
                nome = "Artista"
                comprimentoMaximo = 30
            }
            val faixa = Entrada(musica.uid, TipoCampo.NUMERO).apply {
                nome = "N° faixa"
                comprimentoMaximo = 2
            }

            RoomDb.getInstancia().templateDao().addOuAtualizar(Mapeador.entidadeDe(musica))
            RoomDb.getInstancia().entradaDao().addOuAtualizar(Mapeador.entidadeDe(artista))
            RoomDb.getInstancia().entradaDao().addOuAtualizar(Mapeador.entidadeDe(faixa))


            val jogos = Template("Jogos")
            val genero = Entrada(jogos.uid, TipoCampo.TEXTO).apply {
                nome = "Genero"
                comprimentoMaximo = 30
            }
            val jaJogado = Entrada(jogos.uid, TipoCampo.REAL).apply {
                nome = "Jogado"
            }

            RoomDb.getInstancia().templateDao().addOuAtualizar(Mapeador.entidadeDe(jogos))
            RoomDb.getInstancia().entradaDao().addOuAtualizar(Mapeador.entidadeDe(genero))
            RoomDb.getInstancia().entradaDao().addOuAtualizar(Mapeador.entidadeDe(jaJogado))

        }
    }

    companion object {
        lateinit var get: App
    }
}