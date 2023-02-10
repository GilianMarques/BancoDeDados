package dev.gmarques.bancodedados.data.room

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.dao.PropriedadeDao
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class InstanciaDaoTest : TestCase() {

    private lateinit var campoDao: PropriedadeDao
    private lateinit var db: RoomDb
    private lateinit var instanciaDao: InstanciaDao

    @Before
    public override fun setUp() {
        Log.d("USUK", "TemplateDaoTest.setUp: ")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, RoomDb::class.java).build()

        instanciaDao = db.instanciaDao()
        campoDao = db.campoDao()
    }

    @Test
    fun addOuAtualizarInstanciaCom2Campos() = runBlocking {

        val mInstancia = Instancia().apply { this.templateUid = "templateUid_123" }
        val mInstanciaEntidade = InstanciaEntidade(mInstancia)

        val mCampoDouble = Propriedade(mInstancia.uid, TipoCampo.NUMERO).apply { valorDouble = 999 }
        val mDoubleEntidade = PropriedadeEntidade(mCampoDouble)

        val mCampoBoolean = Propriedade(mInstancia.uid, TipoCampo.REAL).apply { valorBoolean = true }
        val mBooleanEntidade = PropriedadeEntidade(mCampoBoolean)

        campoDao.addOuAtualizar(mDoubleEntidade)
        campoDao.addOuAtualizar(mBooleanEntidade)

        instanciaDao.addOuAtualizar(mInstanciaEntidade)

        val instanciasComCampos = instanciaDao.getInstanciasComCampos(mInstancia.templateUid)

        assertTrue(instanciasComCampos.size == 1)
        assertTrue(instanciasComCampos[0].propriedades.size == 2)

    }

    @After
    fun closeDb() {
        Log.d("USUK", "TemplateDaoTest.closeDb: ")
        db.close()

    }
}