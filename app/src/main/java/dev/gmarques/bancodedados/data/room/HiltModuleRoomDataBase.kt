package dev.gmarques.bancodedados.data.room

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.gmarques.bancodedados.data.room.dao.CampoDao
import dev.gmarques.bancodedados.data.room.dao.InstanciaDao
import dev.gmarques.bancodedados.data.room.dao.PropriedadeDao
import dev.gmarques.bancodedados.data.room.dao.TemplateDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class HiltModuleRoomDataBase {

    @Provides
    @Singleton
    @Synchronized
    fun provideRoomDb(@ApplicationContext context: Context): RoomDataBase {
        return Room.databaseBuilder(
            context, RoomDataBase::class.java, "app-database.sql"
        ).addMigrations(RoomDataBase.Migrations.MIGRATION)
            .build()
    }


    @Provides
    @Singleton
    fun providePropriedadeDao(db: RoomDataBase): PropriedadeDao {
        return db.propriedadeDao()
    }

    @Provides
    @Singleton
    fun provideCampoDao(db: RoomDataBase): CampoDao {
        return db.campoDao()
    }

    @Provides
    @Singleton
    fun provideTemplateDao(db: RoomDataBase): TemplateDao {
        return db.templateDao()
    }

    @Provides
    @Singleton
    fun provideInstanciaDao(db: RoomDataBase): InstanciaDao {
        return db.instanciaDao()
    }
}