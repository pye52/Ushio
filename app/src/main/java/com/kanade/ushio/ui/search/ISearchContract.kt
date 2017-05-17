package com.kanade.ushio.ui.search

import com.kanade.ushio.entity.subject.SubjectSimple

interface ISearchContract {
    interface View : com.kanade.ushio.ui.base.IBaseView {
        fun initDatas(datas: List<SubjectSimple>)

        fun addDatas(datas: List<SubjectSimple>)

        fun loadMoreEnd()
    }

    interface Presenter : com.kanade.ushio.ui.base.IBasePresenter<View> {
        fun query(keyword: String)

        fun loadMore()
    }
}