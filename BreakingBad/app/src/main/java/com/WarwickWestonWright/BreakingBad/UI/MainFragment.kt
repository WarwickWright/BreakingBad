package com.WarwickWestonWright.BreakingBad.UI

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.WarwickWestonWright.BreakingBad.App.App
import com.WarwickWestonWright.BreakingBad.DataObjects.BreakingBadObj
import com.WarwickWestonWright.BreakingBad.R
import com.WarwickWestonWright.BreakingBad.Repos.IMasterImageRepo
import com.WarwickWestonWright.BreakingBad.UI.Lists.BreakingBadListFragment
import com.WarwickWestonWright.BreakingBad.Utilities.BreakingBadSearchFilters
import com.WarwickWestonWright.BreakingBad.ViewModels.BreakingBadObjViewModel

class MainFragment : Fragment() {

    private var iMasterImageRepo: IMasterImageRepo? = null
    private lateinit var rootView: View
    private lateinit var txtSearch: EditText
    private lateinit var btnSearch: Button

    private val app: App = App.getApp() as App
    private val bundle = Bundle()
    private lateinit var breakingBadListFragment: BreakingBadListFragment
    private val breakingBadObjViewModel: BreakingBadObjViewModel by activityViewModels()
    private var breakingBadSearchFilters = BreakingBadSearchFilters()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = inflater.inflate(R.layout.main_fragment, container, false)

        txtSearch = rootView.findViewById(R.id.txtSearch)
        btnSearch = rootView.findViewById(R.id.btnSearch)

        btnSearch.setOnClickListener {
            breakingBadListFragment = BreakingBadListFragment()
            if(txtSearch.text.isNotEmpty()) {
                val objsByName = breakingBadSearchFilters.filterByName(app.breakingBadObjs, txtSearch.text.toString())
                updateMasterList(objsByName)
            }
            else {
                if(iMasterImageRepo == null) {iMasterImageRepo = activity as IMasterImageRepo}
                iMasterImageRepo?.getImageData("", 0)
            }
        }

        breakingBadObjViewModel.selected.observe(viewLifecycleOwner, Observer<MutableList<BreakingBadObj>> { breakingBadObjs ->
            //Update the UI
            //app.breakingBadObjs = breakingBadObjs
            updateMasterList(app.breakingBadObjs)
        })

        return rootView
    }

    private fun updateMasterList(breakingBadObjs: MutableList<BreakingBadObj>) {
        breakingBadListFragment = BreakingBadListFragment()
        bundle.putParcelableArrayList("breakingBadObjs", breakingBadObjs as ArrayList<out Parcelable>)
        breakingBadListFragment.arguments = bundle
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.lltSearchContainer, breakingBadListFragment,"BreakingBadListFragment")?.commit()
    }

}