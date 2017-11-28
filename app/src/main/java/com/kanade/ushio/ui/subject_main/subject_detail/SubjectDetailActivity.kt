package com.kanade.ushio.ui.subject_main.subject_detail

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewCompat
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.transition.Fade
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.allen.library.SuperTextView
import com.blankj.utilcode.util.BarUtils
import com.kanade.ushio.R
import com.kanade.ushio.api.responses.GradeResponse
import com.kanade.ushio.entity.subject.SubjectEp
import com.kanade.ushio.ui.base.BaseActivity
import com.kanade.ushio.ui.subject_main.SubjectGradeDialog
import com.kanade.ushio.ui.subject_main.adapter.SubjectCrtSimpleAdapter
import com.kanade.ushio.ui.widget.GridSpacingItemDecoration
import com.kanade.ushio.utils.strFilter
import java.util.*

/**
 * 番剧详细页面
 * Created by kanade on 2017/2/14.
 */

class SubjectDetailActivity : BaseActivity(), ISubjectDetailContract.View {
    @BindView(R.id.ctl_wrapper)
    lateinit var ctl: CollapsingToolbarLayout
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar

    // ep顶栏信息列表
    @BindView(R.id.subject_cover)
    lateinit var coverIv: ViewGroup
    @BindView(R.id.subject_img)
    lateinit var subjectImg: ImageView
    @BindView(R.id.subject_name)
    lateinit var subjectName: TextView
    @BindView(R.id.subject_simple_summary)
    lateinit var subjectSimpleSummary: TextView
    @BindView(R.id.subject_score)
    lateinit var subjectScore: TextView

    // ep详细信息列表
    @BindView(R.id.subject_type)
    lateinit var subjectType: TextView
    @BindView(R.id.label_subject_story_summary)
    lateinit var labelStorySummary: SuperTextView
    @BindView(R.id.subject_story_summary)
    lateinit var subjectStorySummary: TextView
    @BindView(R.id.subject_summary)
    lateinit var subjectSummary: TextView
    @BindView(R.id.label_subject_crt)
    lateinit var labelSubjectCrt: SuperTextView
    @BindView(R.id.rv_crt)
    lateinit var rv: RecyclerView
    @BindView(R.id.label_subject_progress)
    lateinit var progressSpTv: SuperTextView

    private lateinit var presenter: ISubjectDetailContract.Presenter
    private lateinit var adapter: SubjectCrtSimpleAdapter
    private val gradeDialog: SubjectGradeDialog by lazy { SubjectGradeDialog(context, singleButtonCallback) }

    companion object {
        private const val ARG_SUBJECT_ID = "arg_subject_id"
        @JvmStatic
        fun newInstance(ctx: Context, id: Int): Intent =
                Intent(ctx, SubjectDetailActivity::class.java)
                        .putExtra(ARG_SUBJECT_ID, id)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStatusBar()
        setContentView(R.layout.activity_subject)
        ButterKnife.bind(this)
        ctl.isTitleEnabled = false
        ctl.expandedTitleGravity = GravityCompat.START
        initToolbarNav(toolbar)
        toolbar.setTitle(R.string.label_subject_summary)
        adapter = SubjectCrtSimpleAdapter(Collections.emptyList())
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.addItemDecoration(GridSpacingItemDecoration(2, 20, false))
        rv.adapter = adapter
        rv.isNestedScrollingEnabled = false

        val subjectId = intent.getIntExtra(ARG_SUBJECT_ID, -1)
        if (subjectId == -1) { finish(); return }
        presenter = SubjectDetailPresenter()
        presenter.attach(this)
        presenter.start(subjectId)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detach()
    }

    override fun initSubjectEp(ep: SubjectEp) {
        initToolbarSubjectInfo(ep)
        initBodySubjectInfo(ep)
    }

    override fun setImgBg(url: String, width: Int, height: Int) {
//        val loader = ImageLoader.Builder()
//                .width(width)
//                .height(height)
//                .url(url)
//                .build()
//        ImageLoaderUtil.loadImageWithBlur(context, 20, loader, { d -> ctl.background = d })
    }

    override fun setImg(url: String, width: Int, height: Int) {
//        val loader = ImageLoader.Builder()
//                .width(width)
//                .height(height)
//                .url(url)
//                .placeHolder(R.drawable.img_on_load)
//                .imgView(subjectImg)
//                .build()
//        ImageLoaderUtil.loadImage(context, loader)
    }

    override fun startAct(intent: Intent) = startActivity(intent)

    override fun showGradeDialog(response: GradeResponse) {
        gradeDialog.setDatas(response)
        gradeDialog.show()
    }

    private fun initToolbarSubjectInfo(ep: SubjectEp) {
        subjectName.text = strFilter(ep.nameCn, ep.name, 12)
        val summary = if (ep.summary.length >= 50) "${ep.summary.substring(0, 50)}..." else ep.summary
        subjectSimpleSummary.text = summary
        subjectScore.text = getString(R.string.subject_score, ep.rating.score.toString(), ep.rating.total)
    }

    private fun initBodySubjectInfo(ep: SubjectEp) {
        subjectType.text = ep.typeDetail
        if (TextUtils.isEmpty(ep.summary)) {
            hideSummaryView()
        } else {
            subjectStorySummary.text = ep.summary
        }

        subjectSummary.text = getString(R.string.subject_summary, ep.nameCn, ep.eps.size, ep.airDate, ep.airWeekdayStr)

        if (ep.crt == null || ep.crt.isEmpty()) {
            hideCrtView()
        } else {
            val crtDetailList = if (ep.crt.size > 10) ep.crt.subList(0, 9) else ep.crt
            adapter.setNewData(crtDetailList)
        }

        if (ep.eps.isEmpty()) {
            hideProgressView()
        }
    }

    private fun hideSummaryView() {
        labelStorySummary.visibility = GONE
        subjectStorySummary.visibility = GONE
    }

    private fun hideCrtView() {
        labelSubjectCrt.visibility = GONE
        rv.visibility = GONE
    }

    private fun hideProgressView() {
        progressSpTv.visibility = GONE
    }

    @OnClick(R.id.subject_refresh, R.id.label_subject_summary_staff, R.id.label_subject_crt, R.id.label_subject_progress, R.id.subject_grade)
    fun OnClick(view: View) = presenter.onClick(view)

    private val singleButtonCallback by lazy {
        MaterialDialog.SingleButtonCallback { _, _ -> presenter.updateGrade(gradeDialog.getStatus(), gradeDialog.getRating(), gradeDialog.getComment()) } }

    // 设置全屏
    private fun setStatusBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            window.enterTransition = Fade()
            window.exitTransition = Fade()
            val mChildView = (findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)).getChildAt(0)
            if (mChildView != null)
                ViewCompat.setFitsSystemWindows(mChildView, false)

        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            val mContentView = findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
            val statusBarView = mContentView.getChildAt(0)

            if (statusBarView != null && statusBarView.layoutParams != null && statusBarView.layoutParams.height == BarUtils.getStatusBarHeight())
                mContentView.removeView(statusBarView)
            if (statusBarView != null)
                ViewCompat.setFitsSystemWindows(statusBarView, false)
        }
    }
}