package com.kanade.ushio.ui.subscribe;

import com.kanade.ushio.R;
import com.kanade.ushio.ui.base.BasePresenter;
import com.kanade.ushio.utils.SharePreferenceUtils;

import io.reactivex.disposables.Disposable;

public class SubscribeListPresenter extends BasePresenter<ISubscribeListContract.View> implements ISubscribeListContract.Presenter {
    private UserCollectionModel model;

    @Override
    public void attach(ISubscribeListContract.View v) {
        super.attach(v);
        model = new UserCollectionModel();
        model.start();
        int userId = SharePreferenceUtils.INSTANCE.getUserId();
        Disposable d = model.getCollectionsFromServer(userId)
                .doOnSubscribe(disposable -> view.enableRefreshing())
                .doOnComplete(() -> view.disableRefreshing())
                .subscribe(list -> { view.showList(list); });
        addDisposable(d);
    }

    @Override
    public void detach() {
        super.detach();
        model.stop();
        model = null;
    }

    @Override
    public void initDatas() {
        int userId = SharePreferenceUtils.INSTANCE.getUserId();
        Disposable d = model.getCollections(userId)
                .doOnSubscribe(disposable -> view.enableRefreshing())
                .doOnComplete(() -> view.disableRefreshing())
                .subscribe(list -> view.showList(list));
        addDisposable(d);
    }

    // 刷新记录一定是从网络获取
    @Override
    public void refresh() {
        int userId = SharePreferenceUtils.INSTANCE.getUserId();
        Disposable d = model.getCollectionsFromServer(userId)
                .doOnSubscribe(disposable -> view.enableRefreshing())
                .doOnComplete(() -> view.disableRefreshing())
                .subscribe(list -> { view.showList(list); view.notice(R.string.success); });
        addDisposable(d);
    }
}
