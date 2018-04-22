package com.kanade.ushio.ui.user_collection

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.arch.AppDatabase
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.UserCollectionViewModel
import com.kanade.ushio.ui.subject.SubjectActivity
import com.kanade.ushio.ui.subject.detail.SubjectDetailActivity
import com.kanade.ushio.utils.IO2MainThread
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_user_collection.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment

class UserCollectionFragment : SupportFragment(), SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    private lateinit var adapter: UserCollectionAdapter
    private lateinit var viewModel: UserCollectionViewModel
    private val disposable = CompositeDisposable()

    companion object {
        @JvmStatic
        fun newInstance() = UserCollectionFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user_collection, container, false)
        val subjectDao = AppDatabase.getInstance()
                .subjectDao()

        val factory = Injection.provideUserCollectionViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(UserCollectionViewModel::class.java)

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

        refresh()
    }

    override fun onRefresh() {
        refresh()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return true
    }

    private fun refresh() {
        disposable.add(
                viewModel.queryCollection()
                        .IO2MainThread()
                        .doOnSubscribe { srl.isRefreshing = true }
                        .subscribeOn(AndroidSchedulers.mainThread())
                        .doOnComplete { srl.isRefreshing = false }
                        .subscribe ({
                            adapter.setNewData(it)
                        }, {
                            srl.isRefreshing = false
                        })
        )
    }

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val item = adapter.getItem(position) ?: return
            val intent = SubjectDetailActivity.newIntent(context, item.id)
            startActivity(intent)
        }

        override fun onItemChildClick(a: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
            super.onItemChildClick(adapter, view, position)
            val item = adapter.getItem(position) ?: return
            val intent = SubjectActivity.progressIntent(context, item.id)
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        disposable.dispose()
    }
}