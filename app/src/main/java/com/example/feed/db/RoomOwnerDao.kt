package com.example.feed.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface roomOwnerDao{
    @Query("SELECT * FROM RoomOwner ")
    fun allComponents():List<RoomOwner>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomOwner:RoomOwner)

    @Query("SELECT id FROM RoomOwner WHERE name = :name LIMIT 1")
    fun getSimilar(name: String): Long?

}