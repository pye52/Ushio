package com.kanade.ushio.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.kanade.ushio.R;
import com.kanade.ushio.ui.base.IBaseDelegate;
import com.kanade.ushio.ui.base.IBasePresenter;
import com.kanade.ushio.ui.base.IBaseView;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * 所有fragment的基类，不提供手势返回操作
 * @param <T> 继承{@link IBasePresenter}的presenter实现类
 * Created by kanade on 2017/2/13.
 */

public abstract class BaseFragment<T extends IBasePresenter> extends SupportFragment implements IBaseDelegate<T>, IBaseView {
    protected final String TAG = getClass().getSimpleName();

    protected T presenter;
    private MaterialDialog processDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = createPresenter();
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void onEnterAnimationEnd(Bundle savedInstanceState) {
        super.onEnterAnimationEnd(savedInstanceState);
        presenter.attach(this);
    }

    @Override
    public void notice(int content) {
        ToastUtils.showLong(content);
    }

    @Override
    public void notice(String content) {
        ToastUtils.showLong(content);
    }

    @Override
    public void showProcessDialog(boolean cancelable) {
        if (processDialog == null) {
            initProcessDialog();
        }
        processDialog.setCancelable(cancelable);
        if (!processDialog.isShowing()) {
            processDialog.show();
        }
    }

    @Override
    public void dismissProcessDialog() {
        if (processDialog != null && processDialog.isShowing()) {
            processDialog.dismiss();
        }
    }

    private void initProcessDialog() {
        processDialog = new MaterialDialog.Builder(getContext())
                .content(R.string.please_wait)
                .progressIndeterminateStyle(false)
                .progress(true, 0)
                .build();
    }

    /**
     * 设置工具栏后退按钮
     * 若要显示后退按钮，需调用该方法
     * @param toolbar 要设置的导航栏
     */
    protected void initToolbarNav(Toolbar toolbar) {
        View.OnClickListener navOnClickListener = v -> _mActivity.onBackPressedSupport();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(navOnClickListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideSoftInput();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProcessDialog();
        presenter.detach();
        presenter = null;
    }
}
