package com.example.feed.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "amount")
    var amount:Int
) {
}