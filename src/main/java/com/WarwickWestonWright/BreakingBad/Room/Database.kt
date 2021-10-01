package com.WarwickWestonWright.BreakingBad.Room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BreakingBadEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun BreakingBadDao(): BreakingBadDao
}