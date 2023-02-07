package dev.gmarques.bancodedados.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.gmarques.bancodedados.App
import dev.gmarques.bancodedados.data.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.entidades.CampoUiEntidade
import dev.gmarques.bancodedados.data.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.data.room.dao.CampoUiDao
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.data.room.dao.TemplateDao

@Database(
    entities = [CampoEntidade::class, CampoUiEntidade::class, InstanciaEntidade::class, TemplateEntidade::class],
    version = 1,
    exportSchema = true
)

abstract class RoomDb : RoomDatabase() {

    abstract fun campoDao(): CampoDao
    abstract fun campoUiDao(): CampoUiDao
    abstract fun templateDao(): TemplateDao
    abstract fun instanciaDao(): InstanciaDao

    companion object {

        @Volatile
        private var INSTANCIA: RoomDb? = null

        @Synchronized // use as classes repositorio pra I/O no banco
        fun getInstancia() =
                INSTANCIA ?: criarDb().also { INSTANCIA = it }

        private fun criarDb() =
                Room.databaseBuilder(
                    App.get.applicationContext, RoomDb::class.java, "app-database.sql"
                ).build()

    }

}