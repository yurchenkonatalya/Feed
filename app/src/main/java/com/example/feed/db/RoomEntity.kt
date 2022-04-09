package com.example.feed.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity( foreignKeys = [ForeignKey( entity = RoomOwner::class, parentColumns = arrayOf("id"), childColumns = arrayOf("id_owner"), onDelete = ForeignKey.CASCADE )] )
data class RoomEntity(
    @PrimaryKey(autoGenerate = true)
    var id:Long = 0,

    @ColumnInfo(name = "date")
    var date: String,

    @ColumnInfo(name = "amount")
    var amount:Int,

    @ColumnInfo(name = "id_owner")
    var id_owner:Long

) {
}