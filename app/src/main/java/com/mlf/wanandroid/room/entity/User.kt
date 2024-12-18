package com.mlf.wanandroid.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User(
    var username: String = "", var password: String = "",
    var repassword: String = "", @PrimaryKey var id: Int = 0
) {
    override fun toString(): String {
        return "User(username='$username', password='$password', repassword='$repassword', id=$id)"
    }
}
