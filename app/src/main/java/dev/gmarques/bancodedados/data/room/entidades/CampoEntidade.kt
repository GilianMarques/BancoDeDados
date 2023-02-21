package dev.gmarques.bancodedados.data.room.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import java.util.*

@Entity(tableName = "campos")
class CampoEntidade {

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "nome")
    var nome = ""

    @ColumnInfo(name = "template_uid")
    var templateUid = ""

    @ColumnInfo(name = "tipo_campo")
    lateinit var tipoCampo: TipoCampo

    @ColumnInfo(name = "pode_ser_vazio")
    var podeSerVazio = false

    @ColumnInfo(name = "comprimento_maximo")
    var comprimentoMaximo = 25

    @ColumnInfo(name = "comprimento_minimo")
    var comprimentoMinimo = 0

    @ColumnInfo(name = "maior_que")
    var maiorQue = -999_999

    @ColumnInfo(name = "menor_que")
    var menorQue = 999_999


}
