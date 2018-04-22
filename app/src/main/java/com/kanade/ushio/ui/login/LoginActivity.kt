package com.kanade.ushio.ui.login

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kanade.ushio.R
import com.kanade.ushio.arch.viewmodel.LoginViewModel
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.toolbar.*
import android.content.Intent
import android.net.Uri
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.factory.UserTokenDaoViewModelFactory
import com.kanade.ushio.ui.main.MainActivity
import com.kanade.ushio.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity() {
    private lateinit var factory: UserTokenDaoViewModelFactory
    private lateinit var viewModel: LoginViewModel

    private val processDialogs by lazy {
        MaterialDialog.Builder(this)
                .progress(true, 0)
                .build()
    }
    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        factory = Injection.provideUserTokenViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(LoginViewModel::class.java)

        toolbar.setTitle(R.string.login_page_title)
        btn_auth.setOnClickListener {
            // 跳转到浏览器进行授权操作
            val intent = Intent("android.intent.action.VIEW")
            // 目前采取硬编码的方式
            intent.data = Uri.parse("https://bgm.tv/oauth/authorize?client_id=$APP_ID&response_type=code&redirect_uri=$REDIRECT_URI")
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        // 检查是否从浏览器授权跳转
        if (intent.data != null) {
            loginByBrowser()
        } else if (getUserId() != -1L) {
            disposable.add(viewModel.queryUserToken()
                    .IO2MainThread()
                    .doOnSubscribe {
                        processDialogs.show()
                    }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .filter { token ->
                        val lastLoginTime = getLastLoginTime()
                        val currentTime = System.currentTimeMillis()
                        // 注意单位，本地保存的是毫秒，而token的是秒
                        // 过期则提示用户重新登录
                        if ((lastLoginTime - currentTime) < (token.expires * 1000)) {
                            // 未过期则不需要刷新，直接跳转即可
                            MainActivity.startActivity(this)
                            return@filter false
                        } else {
                            return@filter true
                        }
                    }
                    .observeOn(Schedulers.io())
                    .subscribe ({
                        refreshToken(it.refreshToken)
                    }, {
                        processDialogs.dismiss()
                        ToastUtils.showLong(R.string.net_error)
                        LogUtils.file(it.message)
                    }, {
                        processDialogs.dismiss()
                    }))
        }
        // 以上条件都不符合则说明未在本机登录过，不作任何动作
    }

    private fun refreshToken(refreshToken: String) {
        disposable.add(
                viewModel.refresh(refreshToken)
                        .IO2MainThread()
                        .subscribe ({
                            // 跳转到主界面
                            MainActivity.startActivity(this)
                        }, {
                            processDialogs.dismiss()
                            ToastUtils.showLong(R.string.net_error)
                            LogUtils.file(it.message)
                        }))
    }

    private fun loginByBrowser() {
        val uri = intent.data
        val code = uri.getQueryParameter("code")
        // 利用返回的的code换取Access Token
        disposable.add(viewModel.login(code)
                .IO2MainThread()
                .doOnSubscribe {
                    processDialogs.show()
                }
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe({ token ->
                    if (token != null) {
                        MainActivity.startActivity(this)
                    }
                }, {
                    processDialogs.dismiss()
                    ToastUtils.showLong(R.string.net_error)
                    LogUtils.file(it.message)
                }))
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.dispose()
        processDialogs.dismiss()
    }
}