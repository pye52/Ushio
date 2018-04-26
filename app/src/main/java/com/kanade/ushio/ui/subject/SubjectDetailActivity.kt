package com.kanade.ushio.ui.subject

import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.view.GravityCompat
import android.support.v7.widget.GridLayoutManager
import android.text.TextUtils
import android.transition.Fade
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.adapter.SubjectDetailCrtAdapter
import com.kanade.ushio.adapter.SubjectProgressAdapter
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.SubjectDetailViewModel
import com.kanade.ushio.entity.LargeSubject
import com.kanade.ushio.ui.widget.GridSpacingItemDecoration
import com.kanade.ushio.ui.widget.SubjectGradeDialog
import com.kanade.ushio.utils.GlideApp
import com.kanade.ushio.utils.IO2MainThread
import com.kanade.ushio.utils.strFilter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import jp.wasabeef.glide.transformations.BlurTransformation
import kotlinx.android.synthetic.main.activity_subject_detail.*
import kotlinx.android.synthetic.main.view_subject_detail_body.*
import kotlinx.android.synthetic.main.view_subject_detail_cover.*
import me.yokeyword.fragmentation.SupportActivity
import java.util.*
import kotlin.collections.ArrayList

/**
 * 番剧详细页面
 * Created by kanade on 2017/2/14.
 */

class SubjectDetailActivity : SupportActivity(), View.OnClickListener, SubjectGradeDialog.GradeDialogOnClickListener {
    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newIntent(ctx: Context?, id: Long): Intent =
                Intent(ctx, SubjectDetailActivity::class.java)
                        .putExtra(ARG_SUBJECT_ID, id)
    }

    private lateinit var adapter: SubjectDetailCrtAdapter
    private lateinit var progressAdapter: SubjectProgressAdapter
    private lateinit var viewModel: SubjectDetailViewModel
    private val processDialogs by lazy {
        MaterialDialog.Builder(this)
                .progress(true, 0)
                .build()
    }
    private val gradeDialog by lazy {
        SubjectGradeDialog().apply { setListener(this@SubjectDetailActivity) }
    }
    private val disposable = CompositeDisposable()
    private lateinit var subject: LargeSubject

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setContentView(R.layout.activity_subject_detail)

        toolbar.setNavigationIcon(R.drawable.ic_back)
        toolbar.setNavigationOnClickListener({
            onBackPressed()
        })
        toolbar.setTitle(R.string.subject_detail_summary)

        ctl.isTitleEnabled = false
        ctl.expandedTitleGravity = GravityCompat.START

        adapter = SubjectDetailCrtAdapter(Collections.emptyList())
        rv_crt.layoutManager = GridLayoutManager(this, 2)
        rv_crt.addItemDecoration(GridSpacingItemDecoration(2, 20, false))
        rv_crt.adapter = adapter
        rv_crt.isNestedScrollingEnabled = false
        rv_crt.addOnItemTouchListener(crtListener)

        val factory = Injection.provideSubjectDetailViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(SubjectDetailViewModel::class.java)

        val subjectId = intent.getLongExtra(ARG_SUBJECT_ID, -1)
        if (subjectId == -1L) {
            finish()
            ToastUtils.showLong(R.string.subject_error)
            return
        }
        disposable.add(
                viewModel.querySubject(subjectId)
                        .IO2MainThread()
                        .doOnSubscribe { processDialogs.show() }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { processDialogs.dismiss() }
                        .subscribe({ subject ->
                            this.subject = subject
                            // 获取番组信息后更新界面控件
                            subject_name.text = strFilter(subject.nameCn, subject.name, 12)
                            subject.rating?.let { rating ->
                                subject_score.text = getString(R.string.subject_score, rating.score.toString(), rating.total, subject.rank)
                            }
                            // 当番剧没有简介信息时，隐藏相关的控件
                            initSummaryView(subject)
                            subject_detail_staff.text = getString(R.string.subject_info, subject.nameCn, subject.eps?.size
                                    ?: 0, subject.airDate, subject.getAirWeekdayStr())
                            // 当番剧没有角色信息时，隐藏相关控件
                            initCrtView(subject)
                            initEpsView(subject)
                            initImageView(subject)
                        }, {
                            it.printStackTrace()
                            LogUtils.file(it.message)
                            processDialogs.dismiss()
                        })
        )

        subject_grade.setOnClickListener(this)
        subject_detail_staff_ll.setOnClickListener(this)
        subject_detail_progress_ll.setOnClickListener(this)
        subject_detail_crt_ll.setOnClickListener(this)
    }

    private fun initImageView(it: LargeSubject) {
        // 加载背景图片(模糊)和番剧图
        GlideApp.with(this)
                .asDrawable()
                .load(it.images.getImageL2S())
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .apply(RequestOptions.bitmapTransform(BlurTransformation(25, 3)))
                .into(object : SimpleTarget<Drawable>() {
                    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
                        ctl.background = resource
                    }
                })

        GlideApp.with(this)
                .load(it.images.getImageL2S())
                .placeholder(R.drawable.img_on_load)
                .error(R.drawable.img_on_error)
                .into(subject_img)
    }

    private fun initSummaryView(it: LargeSubject) {
        subject_detail_type.text = it.getTypeDetail()
        if (TextUtils.isEmpty(it.summary)) {
            subject_detail_summary_ll.visibility = GONE
            subject_detail_summary.visibility = GONE
        } else {
            subject_detail_summary.text = it.summary
        }
    }

    private fun initEpsView(it: LargeSubject) {
        it.eps?.let { list ->
            if (list.isEmpty()) {
                subject_detail_progress_ll.visibility = GONE
                rv_progress.visibility = GONE
            } else {
                // 该页面最多显示12项进度，其余在新的页面显示
                val eps = list.subList(0, 12)
                progressAdapter = SubjectProgressAdapter(eps)

                rv_progress.layoutManager = GridLayoutManager(this, 6)
                rv_progress.addItemDecoration(GridSpacingItemDecoration(6, 20, true))
                rv_progress.adapter = progressAdapter
                rv_progress.addOnItemTouchListener(progressListener)
                rv_progress.isNestedScrollingEnabled = false
            }
        }
    }

    private fun initCrtView(it: LargeSubject) {
        it.crt?.let { list ->
            if (list.isEmpty()) {
                subject_detail_crt_ll.visibility = GONE
                rv_crt.visibility = GONE
            } else {
                // 主要角色最多只显示9个，其余在新的页面显示
                val crtDetailList = if (list.size > 10) list.subList(0, 9) else list
                adapter.setNewData(crtDetailList)
            }
        } ?: let {
            subject_detail_crt_ll.visibility = GONE
            rv_crt.visibility = GONE
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.subject_grade -> {
                // 向服务器请求该番剧收藏信息，以填充对话框对应控件
                disposable.add(
                        viewModel.querySubjectCollection(subject.id)
                                .IO2MainThread()
                                .doOnSubscribe { processDialogs.show() }
                                .subscribeOn(AndroidSchedulers.mainThread())
                                .subscribe ({ collection ->
                                    processDialogs.dismiss()
                                    gradeDialog.setAction(collection.status.type)
                                            .setRating(collection.rating)
                                            .setComment(collection.comment)
                                            .setTags(collection.tag)
                                            .setPrivacy(collection.private)
                                            .show(fragmentManager, "dialog")

                                }, {
                                    it.printStackTrace()
                                    processDialogs.dismiss()
                                    LogUtils.file(it.message)
                                })
                )
            }
            R.id.subject_detail_staff_ll -> {
                val arrayList = ArrayList(subject.staff)
                val intent = SubjectActivity.staffIntent(this, arrayList)
                startActivity(intent)
            }
            R.id.subject_detail_progress_ll -> {
                val intent = SubjectActivity.progressIntent(this, subject.id)
                startActivity(intent)
            }
            R.id.subject_detail_crt_ll -> {
                val arrayList = ArrayList(subject.crt)
                val intent = SubjectActivity.crtIntent(this, arrayList)
                startActivity(intent)
            }
        }
    }

    override fun onClick(status: String, comment: String, tags: String, rating: Int, privacy: Int) {
        // 向服务器请求更新该番剧的评论
        disposable.add(
                viewModel.updateSubject(subject.id, status, comment, tags, rating, privacy)
                        .IO2MainThread()
                        .subscribe({
                            ToastUtils.showLong(R.string.comment_success)
                        }, {
                            LogUtils.file(it.message)
                            ToastUtils.showLong(R.string.net_error)
                        })
        )
    }

    private val crtListener = object : OnItemClickListener() {
        override fun onSimpleItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            val arrayList = ArrayList(subject.crt)
            val intent = SubjectActivity.crtIntent(this@SubjectDetailActivity, arrayList)
            startActivity(intent)
        }
    }

    private val progressListener = object : OnItemClickListener() {
        override fun onSimpleItemClick(quickAdapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val intent = SubjectActivity.progressIntent(this@SubjectDetailActivity, subject.id)
            startActivity(intent)
        }
    }

    // 设置全屏
    private fun setStatusBar() {
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.enterTransition = Fade()
        window.exitTransition = Fade()
        val mChildView = (findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)).getChildAt(0)
        if (mChildView != null) {
            mChildView.fitsSystemWindows = false
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        processDialogs.dismiss()
        if (gradeDialog.isVisible) {
            gradeDialog.dismiss()
        }
    }
}