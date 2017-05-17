package com.kanade.ushio.utils.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable

object ImageLoaderUtil {
    const val LOAD_STRATEGY_NORMAL = 0
    const val LOAD_STRATEGY_ONLY_WIFI = 1

    private var mStrategy: BaseImageLoaderProvider = GlideImageLoaderStrategy()

    fun loadImage(context: Context, img: ImageLoader) {
        mStrategy.loadImage(context, img)
    }

    fun loadCircleImage(context: Context, img: ImageLoader) {
        mStrategy.loadCircleImage(context, img)
    }

    fun loadImageWithBlur(ctx: Context, i: Int, img: ImageLoader, ready: (d: Drawable) -> Unit) {
        mStrategy.loadImageWithBlur(ctx, i, img, ready)
    }

    fun setLoadImgStrategy(strategy: BaseImageLoaderProvider) {
        mStrategy = strategy
    }

    fun getBitmap(ctx: Context, url: String, ready: (b: Bitmap) -> Unit) {
        mStrategy.getBitmap(ctx, url, ready)
    }

    fun clearImageDiskCache(context: Context) {
        mStrategy.clearImageDiskCache(context)
    }

    fun clearImageMemoryCache(context: Context) {
        mStrategy.clearImageMemoryCache(context)

    }

    fun trimMemory(context: Context, level: Int) {
        mStrategy.trimMemory(context, level)
    }
}