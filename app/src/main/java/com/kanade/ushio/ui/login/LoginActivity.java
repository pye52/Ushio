package com.kanade.ushio.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.kanade.ushio.R;
import com.kanade.ushio.ui.login.LoginFragment;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * 登录界面
 * Created by kanade on 2017/2/10.
 */

public class LoginActivity extends SupportActivity {
    public static Intent  newInstance(Context ctx) {
        return new Intent(ctx, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.fragment_login_fl, LoginFragment.newInstance());
        }
    }
}
