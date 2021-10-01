package com.WarwickWestonWright.BreakingBad.DeSerializers

import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.Room.BreakingBadEntity
import org.junit.Test

import org.junit.Assert.*

class ObjToEntitiesTest {

    @Test
    fun breakingBadObjToEntity() {
        val objToEntities = ObjToEntities()
        val breakingBadObj = BreakingBadObj()
        breakingBadObj.setName("Warwick Weston Wright")
        breakingBadObj.setOccupation("Android Developer")
        val breakingBadEntity = BreakingBadEntity(0, "Warwick Weston Wright", "", "Android Developer", "", "", "", "", "", "", "", "")
        val entity = objToEntities.breakingBadObjToEntity(breakingBadObj)
        assertEquals(breakingBadEntity, entity)
    }
}