package dev.gmarques.bancodedados

import android.app.Application
import dev.gmarques.bancodedados.data.room.RoomDb

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        get = this
        RoomDb.getInstancia()
    }

    companion object {
        lateinit var get: App
    }
}