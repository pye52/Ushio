package com.kanade.ushio.ui.search

import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.support.v7.widget.Toolbar
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.kanade.ushio.R
import com.kanade.ushio.entity.subject.SubjectSimple
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.subject_main.subject_detail.SubjectDetailActivity
import java.util.*

class SearchFragment : BaseFragment<SearchPresenter>(), ISearchContract.View, SearchView.OnQueryTextListener, BaseQuickAdapter.RequestLoadMoreListener {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.rv_search)
    lateinit var rv: RecyclerView

    private lateinit var searchView: SearchView
    private lateinit var adapter: SearchAdapter

    companion object {
        @JvmStatic
        fun newInstance(): SearchFragment = SearchFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_search, container, false)
        ButterKnife.bind(this, view)
        toolbar.setTitle(R.string.title_nav_search)
        toolbar.inflateMenu(R.menu.menu_search)
        initToolbarNav(toolbar)

        adapter = SearchAdapter(Collections.emptyList())
        adapter.setOnLoadMoreListener(this, rv)
        rv.layoutManager = LinearLayoutManager(context)
        rv.adapter = adapter
        rv.addOnItemTouchListener(listener)
        initSearchView()
        return view
    }

    private fun initSearchView() {
        val item = toolbar.menu.findItem(R.id.search)
        searchView = item.actionView as SearchView
        searchView.inputType = InputType.TYPE_CLASS_TEXT
        searchView.imeOptions = EditorInfo.IME_ACTION_SEARCH
        searchView.queryHint = "搜索"
        searchView.isSubmitButtonEnabled = true
        (searchView.findViewById(R.id.search_button) as ImageView).setImageResource(R.drawable.ic_search)
        (searchView.findViewById(R.id.search_go_btn) as ImageView).setImageResource(R.drawable.ic_search)
        searchView.setOnQueryTextListener(this)
        item.actionView = searchView
        searchView.onActionViewExpanded()
    }

    override fun onLoadMoreRequested() {
        presenter.loadMore()
    }

    override fun initDatas(datas: List<SubjectSimple>) {
        adapter.setNewData(datas)
        adapter.loadMoreComplete()
    }

    override fun addDatas(datas: List<SubjectSimple>) {
        adapter.addData(datas)
        adapter.loadMoreComplete()
    }

    override fun loadMoreEnd() {
        adapter.loadMoreEnd()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query == null || query.isEmpty()) return true
        adapter.setKey(query)
        presenter.query(query)
        hideSoftInput()
        searchView.onActionViewCollapsed()
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean = true

    private val listener = object : OnItemClickListener() {
        override fun onSimpleItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
            val subjectSimple = adapter.getItem(position)
            subjectSimple?.let {
                val intent = SubjectDetailActivity.newInstance(context, it.id)
                startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, view, "img").toBundle())
            }
        }
    }

    override fun createPresenter(): SearchPresenter = SearchPresenter()
}