package com.kanade.ushio.ui.subject.crt

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanade.ushio.R
import com.kanade.ushio.adapter.SubjectCrtAdapter
import com.kanade.ushio.entity.Crt
import kotlinx.android.synthetic.main.fragment_subject_crt.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment

class SubjectCrtFragment : SupportFragment() {

    private lateinit var adapter: SubjectCrtAdapter
    private lateinit var crtList: ArrayList<Crt>

    companion object {
        private const val ARG_SUBJECT_CRT = "arg_subject_crt"
        @JvmStatic
        fun newIntent(list: ArrayList<Crt>): SubjectCrtFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList(ARG_SUBJECT_CRT, list)
            val fragment = SubjectCrtFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_crt, container, false)
        crtList = arguments?.getParcelableArrayList(ARG_SUBJECT_CRT) ?: let {
            activity?.finish()
            return@let arrayListOf<Crt>()
        }
        adapter = SubjectCrtAdapter(crtList)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener({
            activity?.finish()
        })
        toolbar.setTitle(R.string.subject_detail_crt)

        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }
}