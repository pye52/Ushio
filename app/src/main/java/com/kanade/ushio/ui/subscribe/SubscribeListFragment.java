package com.kanade.ushio.ui.subscribe;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.kanade.ushio.R;
import com.kanade.ushio.ui.search.SearchFragment;
import com.kanade.ushio.ui.base.BaseFragment;
import com.kanade.ushio.entity.AniCollection;
import com.kanade.ushio.ui.subject_main.subject_detail.SubjectDetailActivity;

import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;

/**
 * 订阅页面，作为初始页
 * Created by kanade on 2016/11/9.
 */

public class SubscribeListFragment extends BaseFragment<SubscribeListPresenter> implements
        ISubscribeListContract.View, SwipeRefreshLayout.OnRefreshListener, Toolbar.OnMenuItemClickListener {
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.rv_sublist)
    RecyclerView mRvSublist;
    @BindView(R.id.srl_sublist)
    SwipeRefreshLayout mSrlSublist;

    private SubscribeListAdapter adapter;

    public static SubscribeListFragment newInstance() {
        return new SubscribeListFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sublist, container, false);
        ButterKnife.bind(this, view);
        adapter = new SubscribeListAdapter(Collections.emptyList());
        mRvSublist.setLayoutManager(new LinearLayoutManager(getContext()));
        mRvSublist.setAdapter(adapter);
        mRvSublist.addOnItemTouchListener(listener);
        mSrlSublist.setOnRefreshListener(this);
        mToolbar.setTitle(R.string.title_nav_collection);
        mToolbar.inflateMenu(R.menu.main_search);
        mToolbar.setOnMenuItemClickListener(this);
        adapter.bindToRecyclerView(mRvSublist);
        adapter.setEmptyView(R.layout.fragment_sublist_empty);
        return view;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        ((SupportActivity) getActivity()).start(SearchFragment.newInstance());
        return true;
    }

    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        presenter.initDatas();
    }

    @Override
    public void onRefresh() {
        presenter.refresh();
    }

    @NonNull
    @Override
    public SubscribeListPresenter createPresenter() {
        return new SubscribeListPresenter();
    }

    @Override
    public void enableRefreshing() {
        if (!mSrlSublist.isRefreshing()) {
            mSrlSublist.setRefreshing(true);
        }
    }

    @Override
    public void disableRefreshing() {
        if (mSrlSublist.isRefreshing()) {
            mSrlSublist.setRefreshing(false);
        }
    }

    @Override
    public void showList(List<AniCollection> list) {
        adapter.setNewData(list);
    }

    private OnItemClickListener listener = new OnItemClickListener() {
        @Override
        public void onSimpleItemClick(BaseQuickAdapter a, View view, int position) {
            AniCollection aniCollection = adapter.getItem(position);
            if (aniCollection == null) return;
            int subId = aniCollection.getSubjectSimple().getId();
            Intent intent = SubjectDetailActivity.newInstance(getContext(), subId);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), view.findViewById(R.id.sublist_img), "img").toBundle());
        }
    };
}
