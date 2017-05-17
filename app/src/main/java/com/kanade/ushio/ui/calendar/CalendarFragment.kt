package com.kanade.ushio.ui.calendar

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.adapter.models.CalendarSection
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.search.SearchFragment
import com.kanade.ushio.ui.subject_main.subject_detail.SubjectDetailActivity
import me.yokeyword.fragmentation.SupportActivity
import java.util.*

class CalendarFragment : BaseFragment<CalendarPresenter>(), ICalendarContract.View,
        SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rv_calendar)
    lateinit var rv: RecyclerView
    @BindView(R.id.srl_calendar)
    lateinit var mSrlCalendar: SwipeRefreshLayout

    private lateinit var adapter: CalendarAdapter

    companion object {
        @JvmStatic
        fun newInstance(): CalendarFragment = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)
        ButterKnife.bind(this, view)
        toolbar.setTitle(R.string.title_nav_calendar)
        toolbar.inflateMenu(R.menu.main_search)
        toolbar.setOnMenuItemClickListener(this)
        mSrlCalendar.setOnRefreshListener(this)
        adapter = CalendarAdapter(Collections.emptyList())
        rv.layoutManager = GridLayoutManager(context, 2)
        rv.setHasFixedSize(true)
        rv.addOnItemTouchListener(listener)
        rv.adapter = adapter
        return view
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        (activity as SupportActivity).start(SearchFragment.newInstance())
        return true
    }

    override fun showDatas(datas: List<CalendarSection>) {
        adapter.setNewData(datas)
    }

    override fun enableRefreshing() {
        if (!mSrlCalendar.isRefreshing) {
            mSrlCalendar.isRefreshing = true
        }
    }

    override fun disableRefreshing() {
        if (mSrlCalendar.isRefreshing) {
            mSrlCalendar.isRefreshing = false
        }
    }

    override fun onRefresh() {
        presenter.refresh()
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position)
            item?.let { item ->
                // 点击头部项则不作处理
                if (item.isHeader) return
                val subId = item.t.id
                val intent = SubjectDetailActivity.newInstance(context, subId)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "img").toBundle())
            }
        }
    }

    override fun createPresenter(): CalendarPresenter = CalendarPresenter()
}