package com.example.feed.db

import androidx.room.*

@Dao
interface roomDao{
    @Query("SELECT * FROM RoomEntity")
    fun allComponents():List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomEntity:RoomEntity)

}