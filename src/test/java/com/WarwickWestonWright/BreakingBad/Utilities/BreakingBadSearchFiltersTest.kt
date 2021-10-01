package com.WarwickWestonWright.BreakingBad.Utilities

import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import org.junit.Test

import org.junit.Assert.*

class BreakingBadSearchFiltersTest {

    val breakingBadObjs: MutableList<BreakingBadObj> = mutableListOf()
    val breakingBadSearchFilters = BreakingBadSearchFilters()

    @Test
    fun filterByName() {
        val breakingBadObj = BreakingBadObj(1, "Warwick Weston Wright", "", "", "",
                "", "", "", "", "", "", "")
        breakingBadObjs.add(breakingBadObj)
        breakingBadObjs.add(BreakingBadObj(2, "John Doe", "", "", "",
                "", "", "", "", "", "", ""))
        val foundObj = breakingBadSearchFilters.filterByName(breakingBadObjs, "Warwick Weston Wright")[0]
        assertEquals(foundObj.getName(), breakingBadObj.getName())
    }

    @Test
    fun getIndexFromCharId() {
        val breakingBadObj = BreakingBadObj(1, "Warwick Weston Wright", "", "", "",
                "", "", "", "", "", "", "")
        breakingBadObjs.add(breakingBadObj)
        breakingBadObjs.add(BreakingBadObj(2, "John Doe", "", "", "",
                "", "", "", "", "", "", ""))
        val foundIdx = breakingBadSearchFilters.getIndexFromCharId(breakingBadObjs, 2)
        assertEquals(breakingBadObj.getCharId(), 1)
    }

    @Test
    fun allImagesPersisted() {
        val breakingBadObj = BreakingBadObj(1, "Warwick Weston Wright", "", "", "",
                "", "", "", "", "", "", "")
        breakingBadObjs.add(breakingBadObj)
        breakingBadObjs.add(BreakingBadObj(2, "John Doe", "", "", "",
                "Not Empty", "", "", "", "", "", ""))
        val shouldBeFalse = breakingBadSearchFilters.allImagesPersisted(breakingBadObjs)
        assertEquals(shouldBeFalse, false)
        breakingBadObjs[0].setImgB64("Not Empty")
        val shouldBeTrue = breakingBadSearchFilters.allImagesPersisted(breakingBadObjs)
        assertEquals(shouldBeTrue, true)
    }
}