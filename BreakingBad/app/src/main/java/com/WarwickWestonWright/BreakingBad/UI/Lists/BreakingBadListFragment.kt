package com.WarwickWestonWright.BreakingBad.UI.Lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.R

class BreakingBadListFragment : Fragment() {

    private var columnCount = 1
    private val app: App = App.getApp() as App

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { bundle ->
            app.breakingBadObjs = bundle.getParcelableArrayList<BreakingBadObj>("breakingBadObjs")!!
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.breaking_bad_list_fragment, container, false)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = when {
                    columnCount <= 1 -> LinearLayoutManager(context)
                    else -> GridLayoutManager(context, columnCount)
                }
                adapter = BreakingBadViewAdapter(app.breakingBadObjs, activity as BreakingBadViewAdapter.IBreakingBadViewAdapter)
            }
        }
        return view
    }
}