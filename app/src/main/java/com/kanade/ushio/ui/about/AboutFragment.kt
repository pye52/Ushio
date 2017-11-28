package com.kanade.ushio.ui.about

import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.afollestad.materialdialogs.MaterialDialog
import com.blankj.utilcode.util.AppUtils
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.R
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.login.LoginActivity
import com.kanade.ushio.utils.SharePreferenceUtils
import com.kanade.ushio.utils.UPDATE_PATH
import com.kanade.ushio.utils.glide_module.GlideApp

class AboutFragment : BaseFragment<AboutPresenter>(), IAboutContract.View {
    @BindView(R.id.toolbar)
    lateinit var toolbar: Toolbar
    @BindView(R.id.imageView)
    lateinit var iv: ImageView
    @BindView(R.id.version)
    lateinit var versionTv: TextView

    private val logoutDialog by lazy { initLogoutDialog() }
    private val exitappDialog by lazy { initExitappDialog() }
    private val aboutDialog by lazy { initAboutDialog() }

    companion object {
        @JvmStatic
        fun newInstance(): AboutFragment = AboutFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_about, container, false)
        ButterKnife.bind(this, view)
        toolbar.setTitle(R.string.title_nav_me)
        return view
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
        val v = AppUtils.getAppVersionName(context.packageName)
        versionTv.text = "${getString(R.string.label_about_version)}(当前版本: $v)"

        val userAvatar = SharePreferenceUtils.getUserImage()
        GlideApp.with(this)
                .load(userAvatar.large)
                .into(iv)
    }

    @OnClick(R.id.logout, R.id.exitapp, R.id.version, R.id.about)
    fun OnClick(view: View) {
        when (view.id) {
            R.id.logout -> {
                if (!logoutDialog.isShowing) {
                    logoutDialog.show()
                }
            }
            R.id.exitapp -> {
                if (!exitappDialog.isShowing) {
                    exitappDialog.show()
                }
            }
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
            R.id.about -> {
                if (!aboutDialog.isShowing) {
                    aboutDialog.show()
                }
            }
        }
    }

    private fun initLogoutDialog(): MaterialDialog =
            MaterialDialog.Builder(context)
                    .content(R.string.logout_confirm)
                    .positiveText(R.string.certain)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ ->
                        SharePreferenceUtils.setIsLogined(false)
                        val intent = LoginActivity.newInstance(context)
                        startActivity(intent)
                        activity.finish()
                    }
                    .build()

    private fun initExitappDialog(): MaterialDialog =
            MaterialDialog.Builder(context)
                    .content(R.string.exitapp_confirm)
                    .positiveText(R.string.certain)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ -> System.exit(0) }
                    .build()

    private fun initAboutDialog(): MaterialDialog =
            MaterialDialog.Builder(context)
                    .content(R.string.label_about_bangumi_detail)
                    .positiveText(R.string.certain)
                    .build()

    override fun createPresenter(savedInstanceState: Bundle?): AboutPresenter = AboutPresenter()
}