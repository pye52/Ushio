package com.kanade.ushio.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;

public interface IBaseView {
    @NonNull
    Context getContext();

    void notice(int content);

    void notice(String content);

    void showProcessDialog(boolean cancelable);

    void dismissProcessDialog();
}
