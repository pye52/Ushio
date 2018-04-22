package com.kanade.ushio.ui.subject.staff

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kanade.ushio.R
import com.kanade.ushio.adapter.SubjectStaffAdapter
import com.kanade.ushio.entity.Staff
import com.kanade.ushio.utils.strFilter
import kotlinx.android.synthetic.main.fragment_subject_staff.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment

class SubjectStaffFragment : SupportFragment() {

    private lateinit var adapter: SubjectStaffAdapter
    private lateinit var staffList: ArrayList<Staff>

    companion object {
        private const val ARG_SUBJECT_STAFF = "arg_subject_crt"
        @JvmStatic
        fun newIntent(list: ArrayList<Staff>): SubjectStaffFragment {
            val bundle = Bundle()
            bundle.putParcelableArrayList(ARG_SUBJECT_STAFF, list)
            val fragment = SubjectStaffFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_staff, container, false)
        staffList = arguments?.getParcelableArrayList(ARG_SUBJECT_STAFF) ?: let {
            activity?.finish()
            return@let arrayListOf<Staff>()
        }
        adapter = SubjectStaffAdapter(staffList)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener({
            activity?.finish()
        })
        toolbar.setTitle(R.string.subject_detail_staff)

        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
    }
}