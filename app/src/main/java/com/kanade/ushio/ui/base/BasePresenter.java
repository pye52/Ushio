package com.kanade.ushio.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter <T extends IBaseView> implements IBasePresenter<T>{
    protected final String TAG = this.getClass().getSimpleName();

    protected T view;
    protected CompositeDisposable compositeDisposable;

    protected final void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    public void attach(T view) {
        this.view = view;
    }

    @Override
    public void detach() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
            compositeDisposable.clear();
        }
    }

    @NonNull
    public final Context getContext() {
        return view.getContext();
    }
}
