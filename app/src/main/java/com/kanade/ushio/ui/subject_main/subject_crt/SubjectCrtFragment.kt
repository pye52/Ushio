package com.kanade.ushio.ui.subject_main.subject_crt

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.Crt
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.subject_main.adapter.SubjectCrtAdapter
import java.util.*

class SubjectCrtFragment : BaseFragment<SubjectCrtPresenter>(), ISubjectCrtContract.View {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rv_crt)
    lateinit var rv: RecyclerView

    private lateinit var adapter: SubjectCrtAdapter

    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newInstance(epId: Int): SubjectCrtFragment {
            val args = Bundle()
            args.putInt(ARG_SUBJECT_ID, epId)
            val fragment = SubjectCrtFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_crt, container, false)
        ButterKnife.bind(this, view)
        initToolbarNav(toolbar)
        toolbar.setTitle(R.string.label_subject_crt)
        adapter = SubjectCrtAdapter(Collections.emptyList())
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        val epId = arguments.getInt(ARG_SUBJECT_ID)
        presenter.start(epId)
    }

    override fun initDatas(datas: List<Crt>) {
        adapter.setNewData(datas)
    }

    override fun createPresenter(savedInstanceState: Bundle?): SubjectCrtPresenter = SubjectCrtPresenter()
}