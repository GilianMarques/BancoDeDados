package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.room.RoomDb
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import dev.gmarques.bancodedados.domain.modelos.template.Template

import junit.framework.TestCase
import kotlinx.coroutines.runBlocking

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TemplateDaoTest : TestCase() {

    private lateinit var entradaUiDao: EntradaDao
    private lateinit var templateDao: TemplateDao
    private lateinit var db: RoomDb

    @Before
    public override fun setUp() {

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, RoomDb::class.java).build()

        templateDao = db.templateDao()
        entradaUiDao = db.entradaDao()
    }

    @After
    public override fun tearDown() = db.close()

    @Test
    fun getTodosOsTemplates() = runBlocking {

        val template = Template("Musica")
        val campo1 = Entrada(template.uid, TipoCampo.TEXTO).apply {
            nome = "Album"
        }
        val campo2 = Entrada(template.uid, TipoCampo.NUMERO).apply {
            nome = "Posição"
            comprimentoMaximo = 24
            comprimentoMinimo = 1
            podeSerVazio = true
        }

        templateDao.addOuAtualizar(Mapeador.getTemplateEntidade(template))
        entradaUiDao.addOuAtualizar(Mapeador.getEntradaEntidade(campo1))
        entradaUiDao.addOuAtualizar(Mapeador.getEntradaEntidade(campo2))

        val templates = templateDao.getTodosOsTemplates()

        assertTrue(templates.size == 1)
        assertTrue(templates[0].entradas.size == 2)

    }
}