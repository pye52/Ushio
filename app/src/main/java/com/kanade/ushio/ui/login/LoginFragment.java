package com.kanade.ushio.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.kanade.ushio.R;
import com.kanade.ushio.ui.base.BaseFragment;
import com.kanade.ushio.ui.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 登录界面
 * Created by kanade on 2017/2/8.
 */

public class LoginFragment extends BaseFragment<LoginPresenter> implements ILoginContract.View{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.et_login_name)
    EditText mEtLoginName;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.bind(this, view);
        RxTextView.textChanges(mEtLoginName)
                .subscribe(c -> {
                    if (c.toString().length() >= 1 && mEtLoginName.getError() != null) {
                        mEtLoginName.setError(null);
                    }
                });
        mToolbar.setTitle(R.string.login_title);
        return view;
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void setError(int resInt) {
        mEtLoginName.setError(getString(resInt));
        mEtLoginName.requestFocus();
    }

    @Override
    public void popWithStartMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @OnClick(R.id.btn_login)
    public void OnClick() {
        String username = mEtLoginName.getText().toString().trim();
        String userpwd = mEtLoginPwd.getText().toString().trim();
        presenter.login(username, userpwd);
    }
}
