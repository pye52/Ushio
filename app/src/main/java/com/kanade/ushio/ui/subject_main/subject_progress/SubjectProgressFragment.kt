package com.kanade.ushio.ui.subject_main.subject_progress

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.ui.subject_main.adapter.SubjectEpAdapter
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.widget.AutofitRecyclerView
import com.kanade.ushio.ui.widget.EpBottomDialog
import com.kanade.ushio.ui.widget.MarginDecoration
import java.util.*

/**
 * 观看进度页面
 * Created by kanade on 2017/2/14.
 */
class SubjectProgressFragment : BaseFragment<ISubjectProgressContract.Presenter>(), ISubjectProgressContract.View, EpBottomDialog.onBottomClickListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rv_progress)
    lateinit var rv: AutofitRecyclerView

    private lateinit var adapter: SubjectEpAdapter
    private var bottomDialog: EpBottomDialog? = null

    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newInstance(epId: Int): SubjectProgressFragment {
            val args = Bundle()
            args.putInt(ARG_SUBJECT_ID, epId)

            val fragment = SubjectProgressFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_progress, container, false)
        ButterKnife.bind(this, view)
        initToolbarNav(toolbar)
        toolbar.setTitle(R.string.label_subject_progress)
        val margin = ConvertUtils.dp2px(10F)
        adapter = SubjectEpAdapter(Collections.emptyList())
        rv.addOnItemTouchListener(listener)
        rv.addItemDecoration(MarginDecoration(margin))
        rv.adapter = adapter
        return view
    }

    override fun initDatas(datas: List<Ep>) {
        adapter.setNewData(datas)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        val epId = arguments.getInt(ARG_SUBJECT_ID)
        presenter.start(epId)
    }

    override fun showBottomDialog(ep: Ep) {
        dismissBottomDialog()
        bottomDialog = EpBottomDialog(context, ep, this)
        bottomDialog?.show()
    }

    override fun dismissBottomDialog() {
        bottomDialog?.let { if (it.isShowing) it.dismiss() }
    }

    override fun setEpStatus(position: Int) {
        adapter.notifyItemChanged(position)
    }

    override fun createPresenter(): ISubjectProgressContract.Presenter {
        return SubjectProgressPresenter()
    }

    override fun onClick(view: View) {
        presenter.bottomOnClick(view)
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(quickAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position)
            item?.let { presenter.onRvItemClick(item, position) }
        }
    }
}