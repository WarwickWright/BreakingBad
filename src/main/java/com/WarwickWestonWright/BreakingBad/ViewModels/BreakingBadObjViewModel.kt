package com.WarwickWestonWright.BreakingBad.ViewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj

class BreakingBadObjViewModel : ViewModel() {
    val selected = MutableLiveData<MutableList<BreakingBadObj>>()
    fun select(BreakingBadObjs: MutableList<BreakingBadObj>) {
        selected.value = BreakingBadObjs
    }
}