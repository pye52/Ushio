package com.kanade.ushio.ui.about

import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.kanade.ushio.ui.base.BasePresenter

class AboutPresenter : BasePresenter<IAboutContract.View>(), IAboutContract.Presenter {
    override fun update() {
        val updatePath = "https://raw.githubusercontent.com/pye52/Ushio/master/update.json"
        AppUpdater(context)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON(updatePath)
                .start()
    }
}