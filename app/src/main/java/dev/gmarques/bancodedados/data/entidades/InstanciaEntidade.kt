package dev.gmarques.bancodedados.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.Instancia
import java.util.*

@Entity(tableName = "instancias")
class InstanciaEntidade() {

    constructor(x: Instancia) : this() {
        this.templateUid = x.templateUid
        this.uid = x.uid
    }

    @ColumnInfo(name = "template_uid")
    lateinit var templateUid: String

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()


}