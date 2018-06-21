package com.kanade.ushio.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
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
import com.kanade.ushio.adapter.UserCollectionAdapter
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.UserViewModel
import com.kanade.ushio.ui.subject.SubjectActivity
import com.kanade.ushio.ui.subject.SubjectDetailActivity
import com.kanade.ushio.utils.IO2MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.fragment_user_collection.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment
import java.util.*

class UserCollectionFragment : SupportFragment(), SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private lateinit var adapter: UserCollectionAdapter
    private lateinit var viewModel: UserViewModel
    private var loadDisposable: Disposable? = null
    private var refreshDisposable: Disposable? = null

    companion object {
        @JvmStatic
        fun newIntent() = UserCollectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_collection, container, false)
        val subjectDao = AppDatabase.getInstance()
                .subjectDao()

        val factory = Injection.provideUserViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)

        adapter = UserCollectionAdapter(subjectDao, emptyList())
        val emptyView = inflater.inflate(R.layout.fragment_user_collection_empty, null, false)
        adapter.emptyView = emptyView
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        rv.adapter = adapter
        rv.layoutManager = LinearLayoutManager(context)
        rv.addOnItemTouchListener(listener)
        srl.setOnRefreshListener(this)
        toolbar.setTitle(R.string.title_nav_collection)
        toolbar.inflateMenu(R.menu.main_search)
        toolbar.setOnMenuItemClickListener(this)
        adapter.bindToRecyclerView(rv)

        loadDisposable = viewModel.queryCollection()
                        .IO2MainThread()
                        .doOnSubscribe { srl.isRefreshing = true }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .subscribe ({
                            srl.isRefreshing = false
                            adapter.setNewData(it)
                        }, {
                            srl.isRefreshing = false
                            it.printStackTrace()
                        })
    }

    override fun onRefresh() {
        refreshDisposable?.dispose()
        refreshDisposable = viewModel.queryCollectionFromServer()
                .IO2MainThread()
                .subscribe({}, {
                    it.printStackTrace()
                    ToastUtils.showLong(R.string.net_error)
                    adapter.setNewData(Collections.emptyList())
                })
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position) ?: return
            val intent = SubjectDetailActivity.newIntent(context, item.id)
            startActivity(intent)
        }

        override fun onItemChildClick(a: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            val item = adapter.getItem(position) ?: return
            val intent = SubjectActivity.progressIntent(context, item.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        loadDisposable?.dispose()
        refreshDisposable?.dispose()
    }
}