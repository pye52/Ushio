package com.kanade.ushio.ui.base;

public interface IBasePresenter<T extends IBaseView> {
    void attach(T view);

    void detach();
}