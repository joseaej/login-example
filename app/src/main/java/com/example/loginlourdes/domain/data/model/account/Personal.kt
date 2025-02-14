package com.example.loginlourdes.domain.data.model.account

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["id_account"],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)

data class Personal(
    @PrimaryKey
    val nif:String,
    @Embedded
    val addres: Addres,
    @ColumnInfo(name = "id_account")
    val idAccount:Int
)
