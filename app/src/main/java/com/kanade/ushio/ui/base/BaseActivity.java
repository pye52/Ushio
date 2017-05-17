package com.kanade.ushio.ui.base;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.blankj.utilcode.util.ToastUtils;
import com.kanade.ushio.R;

public class BaseActivity extends AppCompatActivity implements IBaseView {
    private MaterialDialog processDialog;

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
        View.OnClickListener navOnClickListener = v -> onBackPressed();

        toolbar.setNavigationIcon(R.drawable.ic_back);
        toolbar.setNavigationOnClickListener(navOnClickListener);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dismissProcessDialog();
    }

    @NonNull
    @Override
    public Context getContext() {
        return this;
    }
}