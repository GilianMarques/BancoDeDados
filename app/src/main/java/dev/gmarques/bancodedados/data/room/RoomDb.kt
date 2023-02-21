package dev.gmarques.bancodedados.data.room

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dev.gmarques.bancodedados.App
import dev.gmarques.bancodedados.data.room.entidades.PropriedadeEntidade
import dev.gmarques.bancodedados.data.room.entidades.CampoEntidade
import dev.gmarques.bancodedados.data.room.entidades.InstanciaEntidade
import dev.gmarques.bancodedados.data.room.entidades.TemplateEntidade
import dev.gmarques.bancodedados.data.room.dao.PropriedadeDao
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.data.room.dao.TemplateDao

@Database(
    entities = [PropriedadeEntidade::class, CampoEntidade::class, InstanciaEntidade::class, TemplateEntidade::class],
    version = 1,
    exportSchema = true
)

abstract class RoomDb : RoomDatabase() {

    abstract fun propriedadeDao(): PropriedadeDao
    abstract fun entradaDao(): CampoDao
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
            )
                .build()


    }

}