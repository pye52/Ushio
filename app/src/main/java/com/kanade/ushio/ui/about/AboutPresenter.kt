package com.kanade.ushio.ui.about

import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.R
import com.kanade.ushio.ui.base.BasePresenter

class AboutPresenter : BasePresenter<IAboutContract.View>(), IAboutContract.Presenter {
    override fun update() {
        val updatePath = "https://raw.githubusercontent.com/pye52/Ushio/master/update.json"
        AppUpdater(context)
                .showAppUpdated(true)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON(updatePath)
                .setTitleOnUpdateAvailable(R.string.update)
                .setTitleOnUpdateNotAvailable(R.string.up_to_date_title)
                .setContentOnUpdateNotAvailable(R.string.up_to_date)
                .setButtonUpdate(R.string.btn_update)
                .setButtonDismiss(R.string.cancel)
                .setButtonDoNotShowAgain("")
                .start()
    }
}