package dev.gmarques.bancodedados.data.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.instancia.Propriedade
import java.util.*

@Entity(tableName = "propriedades")
class PropriedadeEntidade {


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



}
