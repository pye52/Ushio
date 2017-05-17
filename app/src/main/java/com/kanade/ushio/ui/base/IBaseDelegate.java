package com.kanade.ushio.ui.base;

import android.support.annotation.NonNull;

public interface IBaseDelegate<T extends IBasePresenter> {
    @NonNull T createPresenter();
}
