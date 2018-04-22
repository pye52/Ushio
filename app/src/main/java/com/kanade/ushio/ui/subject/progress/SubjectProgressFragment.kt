package com.kanade.ushio.ui.subject.progress

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.ConvertUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.adapter.SubjectProgressAdapter
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.SubjectProgressViewModel
import com.kanade.ushio.entity.Ep
import com.kanade.ushio.ui.widget.EpBottomDialog
import com.kanade.ushio.ui.widget.MarginDecoration
import com.kanade.ushio.utils.IO2MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_subject_progress.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class SubjectProgressFragment : SupportFragment(), EpBottomDialog.onBottomClickListener {
    private lateinit var adapter: SubjectProgressAdapter
    private var bottomDialog: EpBottomDialog? = null
    private val processDialogs by lazy {
        MaterialDialog.Builder(context!!)
                .progress(true, 0)
                .build()
    }

    private lateinit var viewModel: SubjectProgressViewModel
    private val disposable = CompositeDisposable()
    private var updatePosition: Int = 0

    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newIntent(epId: Long): SubjectProgressFragment {
            val args = Bundle()
            args.putLong(ARG_SUBJECT_ID, epId)

            val fragment = SubjectProgressFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_subject_progress, container, false)
        val factory = Injection.provideSubjectProgressViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(SubjectProgressViewModel::class.java)
        adapter = SubjectProgressAdapter(Collections.emptyList())
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener({
            activity?.finish()
        })
        toolbar.setTitle(R.string.subject_detail_progress)

        val margin = ConvertUtils.dp2px(10F)
        rv.addOnItemTouchListener(listener)
        rv.addItemDecoration(MarginDecoration(margin))
        rv.adapter = adapter

        arguments?.let {
            val subjectId = it.getLong(ARG_SUBJECT_ID)
            // 初始化剧集列表
            disposable.add(
                    viewModel.queryEps(subjectId)
                            // 如果列表为空，则需要向服务器请求相关番剧的详细信息
                            // 获取成功后只需要返回剧集列表即可
                            .IO2MainThread()
                            .doOnSubscribe { processDialogs.show() }
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .flatMap { eps ->
                                adapter.setNewData(eps)
                                return@flatMap viewModel.getProgress(subjectId)
                                        .IO2MainThread()
                            }
                            .subscribe({ list ->
                                adapter.setWatchStatus(list)
                                adapter.notifyDataSetChanged()
                                processDialogs.dismiss()
                            }, {
                                processDialogs.dismiss()
                                LogUtils.file(it.message)
                                ToastUtils.showLong(R.string.net_error)
                            })

            )
        } ?: let {
            pop()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        processDialogs.dismiss()
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(quickAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position) ?: return
            updatePosition = position
            if (bottomDialog == null) {
                bottomDialog = EpBottomDialog(context, item, this@SubjectProgressFragment)
            }
            bottomDialog?.let { dialog ->
                if (dialog.isShowing) {
                    dialog.dismiss()
                }
                dialog.setEp(item)
                dialog.show()
            }
        }
    }

    override fun onClick(ep: Ep, statusStr: String?) {
        // 根据点击的选项去请求服务器
        // 当为null时则为选择了"看到这里"
        if (statusStr == null) {
            disposable.add(
                    // 注意第xx话和updatePosition差1(下标从0开始)
                    viewModel.updateAnimationProgress(ep.subjectId, (updatePosition + 1).toLong())
                            .IO2MainThread()
                            .subscribe ({
                                bottomDialog?.dismiss()
                                adapter.watchedHere(updatePosition)
                                adapter.notifyDataSetChanged()
                            }, {
                                bottomDialog?.dismiss()
                                LogUtils.file(it.message)
                                ToastUtils.showLong(R.string.net_error)
                            })
            )
        } else {
            // 这里只需要刷新点击位置的状态即可
            disposable.add(
                    viewModel.updateProgress(ep.id, statusStr)
                            .IO2MainThread()
                            .subscribe({
                                bottomDialog?.dismiss()
                                ep.watchStatus?.setStatus(statusStr)
                                adapter.notifyItemChanged(updatePosition)
                            }, {
                                bottomDialog?.dismiss()
                                LogUtils.file(it.message)
                                ToastUtils.showLong(R.string.net_error)
                            })
            )
        }
    }
}