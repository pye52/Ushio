package com.kanade.ushio.ui.main

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.adapter.CalendarAdapter
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.CalendarViewModel
import com.kanade.ushio.entity.Calendar
import com.kanade.ushio.entity.CalendarSection
import com.kanade.ushio.ui.subject.SubjectDetailActivity
import com.kanade.ushio.utils.IO2MainThread
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_calendar.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class CalendarFragment : SupportFragment(), SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private lateinit var adapter: CalendarAdapter
    private lateinit var viewModel: CalendarViewModel
    private val disposable = CompositeDisposable()

    companion object {
        @JvmStatic
        fun newIntent() = CalendarFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_calendar, container, false)

        val factory = Injection.provideCalendarViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(CalendarViewModel::class.java)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        toolbar.setTitle(R.string.title_nav_calendar)
        toolbar.inflateMenu(R.menu.main_search)
        toolbar.setOnMenuItemClickListener(this)
        srl.setOnRefreshListener(this)
        adapter = CalendarAdapter(Collections.emptyList())
        rv.layoutManager = LinearLayoutManager(context)
        rv.setHasFixedSize(false)
        rv.addOnItemTouchListener(listener)
        rv.adapter = adapter

        disposable.add(
                viewModel.queryCalendar()
                        .IO2MainThread()
                        .subscribe({
                            initRecyclerView(it)
                        }, {
                            it.printStackTrace()
                            LogUtils.file(it.message)
                            ToastUtils.showLong(R.string.net_error)
                        })
        )
    }

    private fun initRecyclerView(it: List<Calendar>) {
        val list = arrayListOf<CalendarSection>()
        it.forEach { calendar ->
            list.add(CalendarSection(true, calendar.weekday?.cn ?: ""))
            calendar.subjects?.map {
                CalendarSection(it)
            }?.toCollection(list)
        }
        adapter.setNewData(list)
        srl.isRefreshing = false
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
//        (activity as SupportActivity).start(SearchFragment.newIntent())
        return true
    }

    override fun onRefresh() {
        disposable.add(
                viewModel.queryCalendarFromServer()
                        .IO2MainThread()
                        .subscribe({
                            initRecyclerView(it)
                        }, {
                            it.printStackTrace()
                            LogUtils.file(it.message)
                            ToastUtils.showLong(R.string.net_error)
                        })
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position) ?: return
            // 点击头部项则不作处理
            if (item.isHeader) return
            val subId = item.t.id
            val intent = SubjectDetailActivity.newIntent(context, subId)
            startActivity(intent,
                    ActivityOptionsCompat.makeSceneTransitionAnimation(activity as Activity, view, "img").toBundle())
        }

        override fun onItemChildClick(a: BaseQuickAdapter<*, *>, view: View?, position: Int) {
            val item = adapter.getItem(position) ?: return
            disposable.add(
                    viewModel.updateSubjectAction(item.t)
                            .IO2MainThread()
                            .subscribe({
                                adapter.notifyItemChanged(position)
                            }, {
                                it.printStackTrace()
                                LogUtils.file(it.message)
                                ToastUtils.showLong(R.string.net_error)
                            }))
        }
    }
}