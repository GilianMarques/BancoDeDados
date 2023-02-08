package dev.gmarques.bancodedados.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.Campo
import java.util.*

@Entity(tableName = "campos")
class CampoEntidade() {

    constructor(mCampo: Campo) : this() {

        this.instanciaUid = mCampo.instanciaUid
        this.uid = mCampo.uid
        this.nome = mCampo.nome
        this.tipoCampo = mCampo.tipoCampo
        this.valorString = mCampo.valorString
        this.valorDouble = mCampo.valorDouble
        this.valorBoolean = mCampo.valorBoolean
    }

    @ColumnInfo(name = "instancia_uid")
    lateinit var instanciaUid: String

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "nome")
    var nome = ""

    @ColumnInfo(name = "tipo_campo")
    lateinit var tipoCampo: TipoCampo

    @ColumnInfo(name = "valor_string")
    var valorString: String = ""

    @ColumnInfo(name = "valor_double")
    var valorDouble = 0

    @ColumnInfo(name = "valor_boolean")
    var valorBoolean = false


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CampoEntidade

        if (instanciaUid != other.instanciaUid) return false
        if (uid != other.uid) return false
        if (nome != other.nome) return false
        if (tipoCampo != other.tipoCampo) return false
        if (valorString != other.valorString) return false
        if (valorDouble != other.valorDouble) return false
        if (valorBoolean != other.valorBoolean) return false

        return true
    }

    override fun hashCode(): Int {
        var result = instanciaUid.hashCode()

        result = 31 * result + uid.hashCode()
        result = 31 * result + nome.hashCode()
        result = 31 * result + tipoCampo.hashCode()
        result = 31 * result + valorString.hashCode()
        result = 31 * result + valorDouble
        result = 31 * result + valorBoolean.hashCode()

        return result
    }


}
