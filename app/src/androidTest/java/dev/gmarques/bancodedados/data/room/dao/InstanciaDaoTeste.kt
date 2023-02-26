package dev.gmarques.bancodedados.data.room.dao

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.Mapeador
import dev.gmarques.bancodedados.data.json_serializador.JacksonJsonSerializador
import dev.gmarques.bancodedados.data.room.RoomDataBase
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import dev.gmarques.bancodedados.domain.modelos.template.Template
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
class InstanciaDaoTest : TestCase() {

    // nao devem ser injetados com hilt
    private lateinit var propriedadeDao: PropriedadeDao
    private lateinit var db: RoomDataBase
    private lateinit var instanciaDao: InstanciaDao

    val mapeador = Mapeador(JacksonJsonSerializador())

    @Before
    public override fun setUp() {
        Log.d("USUK", "TemplateDaoTest.setUp: ")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, RoomDataBase::class.java).build()

        instanciaDao = db.instanciaDao()
        propriedadeDao = db.propriedadeDao()
    }

    @Test
    fun getInstanciasComCampos() = runBlocking {

        val mInstancia = Instancia("").apply { this.templateUid = "templateUid_123" }
        val mInstanciaEntidade = mapeador.getInstanciaEntidade(mInstancia)

        val mCampoDouble =
            Propriedade(mInstancia.uid, TipoCampo.NUMERO).apply { valorDouble = 999.0 }
        val mDoubleEntidade = mapeador.getPropriedadeEntidade(mCampoDouble)

        val mCampoBoolean =
            Propriedade(mInstancia.uid, TipoCampo.BOOLEANO).apply { valorBoolean = true }
        val mBooleanEntidade = mapeador.getPropriedadeEntidade(mCampoBoolean)

        propriedadeDao.addOuAtualizar(mDoubleEntidade)
        propriedadeDao.addOuAtualizar(mBooleanEntidade)

        instanciaDao.addOuAtualizar(mInstanciaEntidade)

        val instanciasComCampos = instanciaDao.getInstanciasComCampos(mInstancia.templateUid)

        assertTrue(instanciasComCampos.size == 1)
        assertTrue(instanciasComCampos[0].propriedades.size == 2)

    }

    @Test
    fun contarInstancias() = runBlocking {

        val template1 = Template("teste_template")
        val template2 = Template("teste_template_2")

        val instancia1 = Instancia(template1.uid)
        val instancia2 = Instancia(template1.uid)
        val instancia3 = Instancia(template1.uid)

        val instancia4 = Instancia(template2.uid)
        val instancia5 = Instancia(template2.uid)

        instanciaDao.addOuAtualizar(mapeador.getInstanciaEntidade(instancia1))
        instanciaDao.addOuAtualizar(mapeador.getInstanciaEntidade(instancia2))
        instanciaDao.addOuAtualizar(mapeador.getInstanciaEntidade(instancia3))

        instanciaDao.addOuAtualizar(mapeador.getInstanciaEntidade(instancia4))
        instanciaDao.addOuAtualizar(mapeador.getInstanciaEntidade(instancia5))


        val contagem1 = instanciaDao.contarInstancias(template1.uid)
        assertTrue(contagem1 == 3)

        val contagem2 = instanciaDao.contarInstancias(template2.uid)
        assertTrue(contagem2 == 2)

    }

    @After
    fun closeDb() {
        Log.d("USUK", "TemplateDaoTest.closeDb: ")
        db.close()

    }
}