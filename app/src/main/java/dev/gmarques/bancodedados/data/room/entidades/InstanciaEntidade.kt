package dev.gmarques.bancodedados.data.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.instancia.Instancia
import java.util.*

@Entity(tableName = "instancias")
class InstanciaEntidade {

    @ColumnInfo(name = "template_uid")
    lateinit var templateUid: String

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

}