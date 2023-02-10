package dev.gmarques.bancodedados.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.TipoCampo
import dev.gmarques.bancodedados.domain.modelos.template.Entrada
import java.util.*

@Entity(tableName = "entradas")
class EntradaEntidade() {

    constructor(entrada: Entrada) : this() {
        this.uid = entrada.uid
        this.nome = entrada.nome
        this.templateUid = entrada.templateUid
        this.tipoCampo = entrada.tipoCampo
        this.podeSerVazio = entrada.podeSerVazio
        this.comprimentoMaximo = entrada.comprimentoMaximo
        this.comprimentoMinimo = entrada.comprimentoMinimo
        this.maiorQue = entrada.maiorQue
        this.menorQue = entrada.menorQue

    }

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
