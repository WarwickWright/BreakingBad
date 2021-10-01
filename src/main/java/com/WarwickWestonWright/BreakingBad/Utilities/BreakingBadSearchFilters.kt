package com.WarwickWestonWright.BreakingBad.Utilities

import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj

class BreakingBadSearchFilters {

    fun filterByName(breakingBadObjs: MutableList<BreakingBadObj>, searchStr: String) : MutableList<BreakingBadObj> {
        if(searchStr.trim() == "") {
            return breakingBadObjs
        }
        val returnVal = mutableListOf<BreakingBadObj>()
        for(breakingBadObj in breakingBadObjs) {
            if(breakingBadObj.getName().contains(searchStr.trim(), true)) {
                returnVal.add(BreakingBadObj(
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
                    breakingBadObj.getBetterCallSaulAppearance())
                )
            }
        }
        return returnVal
    }

    fun getIndexFromCharId(breakingBadObjs: MutableList<BreakingBadObj>, charId: Int) : Int {
        val breakingBadObjsSize = breakingBadObjs.size - 1
        for (i in 0..breakingBadObjsSize) {
            if(breakingBadObjs[i].getCharId() == charId) { return i }
        }
        return -1
    }

    fun allImagesPersisted(breakingBadObjs: MutableList<BreakingBadObj>) : Boolean {
        for(breakingBadObj in breakingBadObjs) {
            if(breakingBadObj.getImgB64() == "") { return false }
        }
        return true
    }

}