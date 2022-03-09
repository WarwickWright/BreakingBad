package com.WarwickWestonWright.BreakingBad.Repos

interface IMasterImageRepo {
    fun getImageData(base64: String, imgId: Int)
}