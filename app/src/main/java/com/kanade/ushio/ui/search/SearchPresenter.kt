package com.kanade.ushio.ui.search

import com.blankj.utilcode.util.LogUtils
import com.kanade.ushio.R
import com.kanade.ushio.api.ApiManager
import com.kanade.ushio.api.SearchService
import com.kanade.ushio.utils.RxJavaUtils

class SearchPresenter : com.kanade.ushio.ui.base.BasePresenter<ISearchContract.View>(), ISearchContract.Presenter {
    private val PAGE_SIZE = 10
    private var keyword: String = ""
    private var curItems = 1 // 当前项数

    override fun query(keyword: String) {
        this.keyword = keyword
        this.curItems = 1
        val searchService = ApiManager.getRetrofit()
                .create(SearchService::class.java)
        val disposable = searchService
                .search(keyword, PAGE_SIZE, curItems)
                .compose(RxJavaUtils.IO2MainThread())
                .subscribe({ response ->
                    view.initDatas(response.list)
                    curItems += PAGE_SIZE
                    if (curItems >= response.results) {
                        view.loadMoreEnd()
                    } })
                { e ->
                    LogUtils.d(TAG, e.toString())
                    view.notice(R.string.no_internet)
                }
        addDisposable(disposable)
    }

    // 这里不知为什么，返回结果会比results少2
    override fun loadMore() {
        curItems++
        val searchService = ApiManager.getRetrofit()
                .create(SearchService::class.java)
        val disposable = searchService
                .search(keyword, PAGE_SIZE, curItems)
                .compose(RxJavaUtils.IO2MainThread())
                .subscribe({ response ->
                    view.addDatas(response.list)
                    curItems += PAGE_SIZE
                    if (curItems >= response.results) {
                        view.loadMoreEnd()
                    } })
                { e ->
                    LogUtils.d(TAG, e.toString())
                    view.notice(R.string.no_internet) }
        addDisposable(disposable)
    }
}