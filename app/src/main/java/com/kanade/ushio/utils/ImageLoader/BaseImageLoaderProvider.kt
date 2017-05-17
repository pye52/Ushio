package com.kanade.ushio.utils.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable

interface BaseImageLoaderProvider {
    fun loadImage(ctx: Context, img: ImageLoader)

    fun loadCircleImage(ctx: Context, img: ImageLoader)

    fun loadImageWithBlur(ctx: Context, i: Int, img: ImageLoader, ready: (d: Drawable) -> Unit)

    fun getBitmap(ctx: Context, url: String, ready: (b: Bitmap) -> Unit)

    //清除硬盘缓存
    fun clearImageDiskCache(context: Context)

    //清除内存缓存
    fun clearImageMemoryCache(context: Context)

    //根据不同的内存状态，来响应不同的内存释放策略
    fun trimMemory(context: Context, level: Int)
}