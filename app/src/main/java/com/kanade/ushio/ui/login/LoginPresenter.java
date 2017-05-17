package com.kanade.ushio.ui.login;

import com.blankj.utilcode.util.LogUtils;
import com.kanade.ushio.R;
import com.kanade.ushio.api.ApiManager;
import com.kanade.ushio.api.AuthService;
import com.kanade.ushio.ui.base.BasePresenter;
import com.kanade.ushio.utils.RxJavaUtils;
import com.kanade.ushio.utils.SharePreferenceUtils;

import io.reactivex.disposables.Disposable;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class LoginPresenter extends BasePresenter<ILoginContract.View> implements ILoginContract.Presenter {
    @Override
    public void login(String username, String userpwd) {
        AuthService service = ApiManager.getRetrofit()
                .create(AuthService.class);
        Disposable d = service.auth(username, userpwd)
                .compose(RxJavaUtils.IO2MainThread())
                .doOnSubscribe(disposable -> view.showProcessDialog(true))
                .doOnComplete(() -> view.dismissProcessDialog())
                .subscribe(user -> {
                            if (user == null) {
                                view.setError(R.string.invalid_name_pwd);
                                return;
                            }

                            SharePreferenceUtils.INSTANCE.saveUserToSp(user);
                            SharePreferenceUtils.INSTANCE.setIsLogined(true);
                            view.popWithStartMain();
                        },
                        throwable -> {
                            LogUtils.e(TAG, throwable.toString());
                            view.notice(R.string.no_internet);
                            view.dismissProcessDialog();
                        });
        addDisposable(d);
    }

    @Override
    public void attach(ILoginContract.View view) {
        super.attach(view);
        if (SharePreferenceUtils.INSTANCE.isLogin()) {
            view.popWithStartMain();
        }
    }
}
