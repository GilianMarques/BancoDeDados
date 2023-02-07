package dev.gmarques.bancodedados.data.room

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.domain.modelos.Instancia
import junit.framework.TestCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.After

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class TemplateDaoTest : TestCase() {

    private lateinit var db: RoomDb
    private lateinit var instanciaDao: InstanciaDao

    @Before
    public override fun setUp() {
        Log.d("USUK", "TemplateDaoTest.setUp: ")

        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(appContext, RoomDb::class.java).build()

        instanciaDao = db.instanciaDao()
    }


    @Test
    fun addOuAtualizar() = runBlocking {
            Log.d("USUK", "TemplateDaoTest.useAppContext: ")

            val x = Instancia().apply { templateUid = "???" }
            val y = InstanciaEntidade(x)
            instanciaDao.addOuAtualizar(y)

            val z = instanciaDao.getTodasInstancias("???")

            assertEquals(z.containsKey(y), true)

    }

    @After
    fun closeDb() {
        Log.d("USUK", "TemplateDaoTest.closeDb: ")
        db.close()

    }
/*
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("dev.gmarques.bancodedados", appContext.packageName)
    }*/
}