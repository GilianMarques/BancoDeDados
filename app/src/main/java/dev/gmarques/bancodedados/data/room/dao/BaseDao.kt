package dev.gmarques.bancodedados.data.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

abstract class BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun addOuAtualizar(vararg users: T)

    @Delete
    abstract suspend fun remover(user: T)
}