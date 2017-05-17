package com.kanade.ushio.ui.login;

import com.kanade.ushio.ui.base.IBasePresenter;
import com.kanade.ushio.ui.base.IBaseView;

public interface ILoginContract {
    interface View extends IBaseView {
        void setError(int resInt);

        void popWithStartMain();
    }

    interface Presenter extends IBasePresenter<View> {
        void login(String username, String userpwd);
    }
}
