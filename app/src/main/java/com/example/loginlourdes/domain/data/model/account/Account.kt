package com.example.loginlourdes.domain.data.model.account

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.loginlourdes.domain.data.model.Email
import java.time.LocalDateTime
import java.util.Date

@Entity
class Account(
    @PrimaryKey
    var id:Int=1,
    var email: Email =Email(""),
    var password:String="",
    var name:String?="",
    val lastName:String?="",
    @ColumnInfo(name = "display_name")
    val displayName:String="",
    var birthDate: Date? =null,
    @ColumnInfo(name = "firebase_uid")
    val firebaseUID:String? ="",
    @ColumnInfo(name = "photo_url")
    val photoURL:String? ="",
    @ColumnInfo(name = "create_at")
    val createAt:String = getCurrentTimestamp(),
    @ColumnInfo(name = "update_at")
    val updated_at:String = getCurrentTimestamp(),
    ) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}
fun getCurrentTimestamp(): String {
    return LocalDateTime.now().toString()
}