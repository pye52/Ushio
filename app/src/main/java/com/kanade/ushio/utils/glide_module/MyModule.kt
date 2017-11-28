package com.kanade.ushio.utils.glide_module

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class MyModule: AppGlideModule() {
    override fun applyOptions(context: Context?, builder: GlideBuilder?) {}

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {

    }

    override fun isManifestParsingEnabled(): Boolean = false
}