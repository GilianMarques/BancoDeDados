package dev.gmarques.bancodedados.data.room

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.domain.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.Campo
import dev.gmarques.bancodedados.domain.modelos.Instancia
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class InstanciaDaoTest : TestCase() {

    private lateinit var campoDao: CampoDao
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

        val mCampoDouble = Campo(mInstancia.uid, TipoCampo.NUMERO).apply { valorDouble = 999 }
        val mDoubleEntidade = CampoEntidade(mCampoDouble)

        val mCampoBoolean = Campo(mInstancia.uid, TipoCampo.REAL).apply { valorBoolean = true }
        val mBooleanEntidade = CampoEntidade(mCampoBoolean)

        campoDao.addOuAtualizar(mDoubleEntidade)
        campoDao.addOuAtualizar(mBooleanEntidade)

        instanciaDao.addOuAtualizar(mInstanciaEntidade)

        val instanciasComCampos = instanciaDao.getInstanciasComCampos(mInstancia.templateUid)

        assertTrue(instanciasComCampos.size == 1)
        assertTrue(instanciasComCampos[0].campos.size == 2)

    }

    @After
    fun closeDb() {
        Log.d("USUK", "TemplateDaoTest.closeDb: ")
        db.close()

    }
}