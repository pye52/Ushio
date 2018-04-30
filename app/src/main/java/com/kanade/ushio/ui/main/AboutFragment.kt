package com.kanade.ushio.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ToastUtils
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.R
import com.kanade.ushio.arch.Injection
import com.kanade.ushio.arch.viewmodel.UserViewModel
import com.kanade.ushio.ui.login.LoginActivity
import com.kanade.ushio.utils.GlideApp
import com.kanade.ushio.utils.IO2MainThread
import com.kanade.ushio.utils.UPDATE_PATH
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.toolbar.*
import me.yokeyword.fragmentation.SupportFragment

class AboutFragment : SupportFragment(), View.OnClickListener {
    private val logoutDialog by lazy { initLogoutDialog() }
    private val exitappDialog by lazy { initExitappDialog() }
    private val aboutDialog by lazy { initAboutDialog() }

    private lateinit var viewModel: UserViewModel
    private val disposable = CompositeDisposable()

    companion object {
        @JvmStatic
        fun newIntent(): AboutFragment {
            return AboutFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        val factory = Injection.provideUserViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(UserViewModel::class.java)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        logout.setOnClickListener(this)
        exitapp.setOnClickListener(this)
        version.setOnClickListener(this)
        about.setOnClickListener(this)

        toolbar.setTitle(R.string.title_nav_me)
        val v = AppUtils.getAppVersionName(context?.packageName)
        val vStr = "${getString(R.string.update)}(当前版本: $v)"
        version.text = vStr

        disposable.add(
                viewModel.queryUser()
                        .IO2MainThread()
                        .subscribe({ user ->
                            GlideApp.with(this)
                                    .load(user.avatar.getImageL2S())
                                    .circleCrop()
                                    .into(imageView)
                        }, {
                            it.printStackTrace()
                            LogUtils.file(it.message)
                            ToastUtils.showLong(R.string.net_error)
                        })
        )
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.logout -> logoutDialog.show()
            R.id.exitapp -> exitappDialog.show()
            R.id.version -> {
                AppUpdater(context)
                        .showAppUpdated(true)
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateJSON(UPDATE_PATH)
                        .setTitleOnUpdateAvailable(R.string.update)
                        .setTitleOnUpdateNotAvailable("")
                        .setContentOnUpdateNotAvailable(R.string.up_to_date)
                        .setButtonUpdate(R.string.btn_update)
                        .setButtonDismiss(R.string.cancel)
                        .setButtonDoNotShowAgain("")
                        .start()
            }
            R.id.about -> aboutDialog.show()
        }
    }

    private fun initLogoutDialog(): MaterialDialog =
            MaterialDialog.Builder(context!!)
                    .content(R.string.logout_confirm)
                    .positiveText(R.string.confirm)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ ->
                        val intent = LoginActivity.newIntent(context)
                        startActivity(intent)
                        activity?.finish()
                    }
                    .build()

    private fun initExitappDialog(): MaterialDialog =
            MaterialDialog.Builder(context!!)
                    .content(R.string.exitapp_confirm)
                    .positiveText(R.string.confirm)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ -> System.exit(0) }
                    .build()

    private fun initAboutDialog(): MaterialDialog =
            MaterialDialog.Builder(context!!)
                    .content(R.string.about_bangumi_detail)
                    .positiveText(R.string.confirm)
                    .build()
}