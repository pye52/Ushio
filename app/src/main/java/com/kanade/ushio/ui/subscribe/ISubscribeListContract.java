package com.kanade.ushio.ui.subscribe;

import com.kanade.ushio.ui.base.IBasePresenter;
import com.kanade.ushio.ui.base.IBaseView;
import com.kanade.ushio.entity.AniCollection;

import java.util.List;

public interface ISubscribeListContract {
    interface View extends IBaseView {
        void enableRefreshing();

        void disableRefreshing();

        void showList(List<AniCollection> list);
    }

    interface Presenter extends IBasePresenter<View> {
        void initDatas();

        void refresh();
    }
}
