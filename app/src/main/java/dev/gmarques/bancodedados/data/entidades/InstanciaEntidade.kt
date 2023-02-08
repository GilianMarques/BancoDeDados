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


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InstanciaEntidade

        if (templateUid != other.templateUid) return false
        if (uid != other.uid) return false

        return true
    }

    override fun hashCode(): Int {
        var result = templateUid.hashCode()
        result = 31 * result + uid.hashCode()
        return result
    }


}