package com.example.feed.db

import androidx.room.*

@Dao
interface roomDao{
    @Query("SELECT * FROM RoomEntity WHERE id_owner = :id_owner")
    fun allComponents(id_owner: Long): List<RoomEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(roomEntity: RoomEntity)

}