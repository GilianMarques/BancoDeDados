package dev.gmarques.bancodedados.data.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.gmarques.bancodedados.domain.modelos.template.Template
import java.util.UUID

@Entity(tableName = "templates")
class TemplateEntidade() {

    constructor(template: Template) : this() {
        this.uid = template.uid
        this.nome = template.nome
    }

    @ColumnInfo(name = "uid")
    @PrimaryKey
    var uid: String = UUID.randomUUID().toString()

    @ColumnInfo(name = "nome")
    var nome = ""
}