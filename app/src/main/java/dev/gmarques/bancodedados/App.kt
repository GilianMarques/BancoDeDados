package dev.gmarques.bancodedados

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.data.room.dao.TemplateDao
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltAndroidApp
class App : Application() {

    @Inject
    lateinit var mapeador: Mapeador

    @Inject
    lateinit var templateDao: TemplateDao

    @Inject
    lateinit var campoDao: CampoDao

    override fun onCreate() {
        super.onCreate()
        get = this
        popularRoom()
    }

    private fun popularRoom() {
        runBlocking {
            if (templateDao.getTodosOsTemplates()
                    .isNotEmpty()
            ) return@runBlocking


            val musica = Template().apply { nome="Musica" }
            val artista = Campo(musica.uid, TipoCampo.TEXTO).apply {
                nome = "Artista"
                comprimentoMaximo = 30
            }
            val faixa = Campo(musica.uid, TipoCampo.NUMERO).apply {
                nome = "N° faixa"
                maiorQue = 0
                menorQue = 99
            }

            templateDao.addOuAtualizar(mapeador.getTemplateEntidade(musica))
            campoDao.addOuAtualizar(mapeador.getEntradaEntidade(artista))
            campoDao.addOuAtualizar(mapeador.getEntradaEntidade(faixa))


            val jogos = Template().apply { nome="Jogo"}
            val genero = Campo(jogos.uid, TipoCampo.TEXTO).apply {
                nome = "Genero"
                comprimentoMaximo = 30
            }
            val jaJogado = Campo(jogos.uid, TipoCampo.BOOLEANO).apply {
                nome = "Jogado"
            }

            templateDao.addOuAtualizar(mapeador.getTemplateEntidade(jogos))
            campoDao.addOuAtualizar(mapeador.getEntradaEntidade(genero))
            campoDao.addOuAtualizar(mapeador.getEntradaEntidade(jaJogado))

        }
    }

    companion object {
        lateinit var get: App
    }
}