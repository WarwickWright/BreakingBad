package com.WarwickWestonWright.BreakingBad.Room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BreakingBadEntity(
    @PrimaryKey val charId: Int,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "birthday") val birthday: String?,
    @ColumnInfo(name = "occupation") val occupation: String?,
    @ColumnInfo(name = "img") val img: String?,
    @ColumnInfo(name = "imgB64") val imgB64: String?,
    @ColumnInfo(name = "status") val status: String?,
    @ColumnInfo(name = "nickname") val nickname: String?,
    @ColumnInfo(name = "appearance") val appearance: String?,
    @ColumnInfo(name = "portrayed") val portrayed: String?,
    @ColumnInfo(name = "category") val category: String?,
    @ColumnInfo(name = "betterCallSaulAppearance") val betterCallSaulAppearance: String?
)
//6th Parameter B64 will hold base64 image data and persist it to room this is an open end that is not in the data set returned