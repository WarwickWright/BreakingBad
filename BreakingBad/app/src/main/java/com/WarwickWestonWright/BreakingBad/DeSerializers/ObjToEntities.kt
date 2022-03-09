package com.WarwickWestonWright.BreakingBad.DeSerializers

import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.Room.BreakingBadEntity

class ObjToEntities {
    fun breakingBadObjToEntity(breakingBadObj: BreakingBadObj) : BreakingBadEntity {
        return BreakingBadEntity(
            breakingBadObj.getCharId(),
            breakingBadObj.getName(),
            breakingBadObj.getBirthday(),
            breakingBadObj.getOccupation(),
            breakingBadObj.getImg(),
            breakingBadObj.getImgB64(),
            breakingBadObj.getStatus(),
            breakingBadObj.getNickname(),
            breakingBadObj.getAppearance(),
            breakingBadObj.getPortrayed(),
            breakingBadObj.getCategory(),
            breakingBadObj.getBetterCallSaulAppearance()
        )
    }
}