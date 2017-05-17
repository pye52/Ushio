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
import com.kanade.ushio.R
import com.kanade.ushio.ui.base.BaseFragment
import com.kanade.ushio.ui.login.LoginActivity
import com.kanade.ushio.utils.ImageLoader.ImageLoader
import com.kanade.ushio.utils.ImageLoader.ImageLoaderUtil
import com.kanade.ushio.utils.SharePreferenceUtils

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
        val loader = ImageLoader.Builder()
                .url(userAvatar.large)
                .imgView(iv)
                .build()
        ImageLoaderUtil.loadCircleImage(context, loader)
    }

    @OnClick(R.id.logout, R.id.exitapp, R.id.version, R.id.about)
    fun OnClick(view: View) {
        when (view.id) {
            R.id.logout -> if (!logoutDialog.isShowing) logoutDialog.show()
            R.id.exitapp -> if (!exitappDialog.isShowing) exitappDialog.show()
            R.id.version -> presenter.update()
            R.id.about -> if (!aboutDialog.isShowing) aboutDialog.show()
        }
    }

    private fun initLogoutDialog(): MaterialDialog =
            MaterialDialog.Builder(context)
                    .content(R.string.logout_confirm)
                    .positiveText(R.string.certain)
                    .negativeText(R.string.cancel)
                    .onPositive { _, _ ->
                        val intent = LoginActivity.newInstance(context)
                        SharePreferenceUtils.setIsLogined(false)
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

    override fun createPresenter(): AboutPresenter = AboutPresenter()
}