package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.json_serializador.JacksonJsonSerializador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Campo
import dev.gmarques.bancodedados.domain.modelos.template.Template

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class TemplateDaoTest : TestCase() {

    // nao devem ser injetados com hilt
    private lateinit var entradaUiDao: CampoDao
    private lateinit var templateDao: TemplateDao
    private lateinit var db: RoomDataBase

    val mapeador = Mapeador(JacksonJsonSerializador())

    @Before
    public override fun setUp() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, RoomDataBase::class.java).build()

        templateDao = db.templateDao()
        entradaUiDao = db.campoDao()
    }

    @After
    public override fun tearDown() = db.close()

    @Test
    fun getTodosOsTemplates() = runBlocking {

        val template = Template("Musica")
        val campo1 = Campo(template.uid, TipoCampo.TEXTO).apply {
            nome = "Album"
        }
        val campo2 = Campo(template.uid, TipoCampo.NUMERO).apply {
            nome = "Posição"
            comprimentoMaximo = 24
            comprimentoMinimo = 1
            podeSerVazio = true
        }

        templateDao.addOuAtualizar(mapeador.getTemplateEntidade(template))
        entradaUiDao.addOuAtualizar(mapeador.getEntradaEntidade(campo1))
        entradaUiDao.addOuAtualizar(mapeador.getEntradaEntidade(campo2))

        val templates = templateDao.getTodosOsTemplates()

        assertTrue(templates.size == 1)
        assertTrue(templates[0].entradas.size == 2)

    }
}