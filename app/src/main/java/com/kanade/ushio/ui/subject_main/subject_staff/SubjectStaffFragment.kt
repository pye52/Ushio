package com.kanade.ushio.ui.subject_main.subject_staff

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.subject_main.adapter.SubjectStaffAdapter
import com.kanade.ushio.utils.strFilter
import java.util.*

class SubjectStaffFragment : BaseFragment<SubjectStaffPresenter>(), ISubjectStaffContract.View {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.staff_summary)
    lateinit var summary: TextView
    @BindView(R.id.rv_staff)
    lateinit var rv: RecyclerView

    private lateinit var adapter: SubjectStaffAdapter

    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newInstance(epId: Int): SubjectStaffFragment {
            val args = Bundle()
            args.putInt(ARG_SUBJECT_ID, epId)
            val fragment = SubjectStaffFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_staff, container, false)
        ButterKnife.bind(this, view)
        toolbar.setTitle(R.string.label_subject_summary_staff)
        initToolbarNav(toolbar)
        adapter = SubjectStaffAdapter(Collections.emptyList())
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        rv.isNestedScrollingEnabled = false
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        val subjectEpId = arguments.getInt(ARG_SUBJECT_ID)
        presenter.start(subjectEpId)
    }

    override fun initData(ep: SubjectEp) {
        val txt = getString(R.string.subject_summary, ep.nameCn, ep.eps.size, ep.airDate, ep.airWeekdayStr)
        val txt2 = StringBuilder("")
        for (staff in ep.staff) {
            for (job in staff.jobs) {
                txt2.append("${job.realmString}: ${strFilter(staff.nameCn, staff.name, 12)}")
            }
            txt2.append("\n")
        }
        summary.text = "$txt\n$txt2"
        adapter.setNewData(ep.staff)
    }

    override fun exit() = activity.finish()

    override fun createPresenter(): SubjectStaffPresenter = SubjectStaffPresenter()
}