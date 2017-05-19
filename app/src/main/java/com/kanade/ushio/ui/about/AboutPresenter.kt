package com.kanade.ushio.ui.about

import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.R
import com.kanade.ushio.ui.base.BasePresenter
import com.kanade.ushio.utils.UPDATE_PATH

class AboutPresenter : BasePresenter<IAboutContract.View>(), IAboutContract.Presenter {
    override fun update() {
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
}