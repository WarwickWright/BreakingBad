package com.WarwickWestonWright.BreakingBad.Room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj

@Dao
interface BreakingBadDao {
    @Query("SELECT * FROM BreakingBadEntity")
    fun getAll(): MutableList<BreakingBadObj>

    @Query("SELECT * FROM BreakingBadEntity WHERE charId IN (:charIds)")
    fun loadAllByIds(charIds: IntArray): MutableList<BreakingBadObj>

    @Query("SELECT * FROM BreakingBadEntity WHERE name LIKE :name")
    fun findByName(name: String): MutableList<BreakingBadObj>

    @Query("UPDATE BreakingBadEntity SET imgB64 = :imgB64 WHERE charId = :charId")
    fun updateImgB64(imgB64: String, charId: Int)

    @Insert
    fun insertAll(vararg breakingBadEntity: BreakingBadEntity)

    //@Insert
    //fun insertBreakingBadObj(vararg breakingBadObj: BreakingBadObj)

    @Delete
    fun delete(breakingBadEntity: BreakingBadEntity)
}