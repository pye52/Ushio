package com.kanade.ushio.utils.ImageLoader

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.blankj.utilcode.util.NetworkUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.stream.StreamModelLoader
import com.bumptech.glide.load.resource.drawable.GlideDrawable
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import jp.wasabeef.glide.transformations.BlurTransformation
import jp.wasabeef.glide.transformations.CropCircleTransformation
import jp.wasabeef.glide.transformations.CropTransformation
import java.io.IOException
import java.io.InputStream

class GlideImageLoaderStrategy : BaseImageLoaderProvider {
    override fun loadImage(ctx: Context, img: ImageLoader) {
        val flag = false
        // 如果不是在wifi下加载图片，直接加载
        if (!flag) {
            loadNormal(ctx, img)
            return
        }

        if (img.wifiStrategy == ImageLoaderUtil.LOAD_STRATEGY_ONLY_WIFI) {
            val netType = NetworkUtils.getNetworkType()
            //如果是在wifi下才加载图片，并且当前网络是wifi,直接加载
            if (netType == NetworkUtils.NetworkType.NETWORK_WIFI) {
                loadNormal(ctx, img)
            } else {
                //如果是在wifi下才加载图片，并且当前网络不是wifi，加载缓存
                loadCache(ctx, img)
            }
        } else {
            //如果不是在wifi下才加载图片
            loadNormal(ctx, img)
        }
    }

    override fun loadCircleImage(ctx: Context, img: ImageLoader) {
        val request = Glide.with(ctx).load(img.url)
        if (img.placeHolder != -1) {
            request.placeholder(img.placeHolder)
        }
        if (img.width != -1 && img.height != -1) {
            request.override(img.width, img.height)
        }
        request.bitmapTransform(CropCircleTransformation(ctx))
                .error(img.errorHolder)
                .into(img.imgView)
    }

    override fun loadImageWithBlur(ctx: Context, i: Int, img: ImageLoader, ready: (d: Drawable) -> Unit) {
        Glide.with(ctx)
                .load(img.url)
                .bitmapTransform(BlurTransformation(ctx, i), CropTransformation(ctx, img.width, img.height))
                .crossFade()
                .into(object : SimpleTarget<GlideDrawable>() {
                    override fun onResourceReady(resource: GlideDrawable, glideAnimation: GlideAnimation<in GlideDrawable>) = ready(resource)
                })
    }

    override fun getBitmap(ctx: Context, url: String, ready: (b: Bitmap) -> Unit) {
        Glide.with(ctx)
                .load(url)
                .asBitmap()
                .into(object : SimpleTarget<Bitmap>() {
                    override fun onResourceReady(resource: Bitmap, glideAnimation: GlideAnimation<in Bitmap>) = ready(resource)
                })
    }

    override fun clearImageDiskCache(context: Context) {
        Glide.get(context).clearDiskCache()
    }

    override fun clearImageMemoryCache(context: Context) {
        Glide.get(context).clearMemory()
    }

    override fun trimMemory(context: Context, level: Int) {
        Glide.get(context).trimMemory(level)
    }

    /**
     * load image with Glide
     */
    private fun loadNormal(ctx: Context, img: ImageLoader) {
        val request = Glide.with(ctx).load(img.url)
        if (img.placeHolder != -1) {
            request.placeholder(img.placeHolder)
        }
        if (img.width != -1 && img.height != -1) {
            request.override(img.width, img.height)
        }
        request.error(img.errorHolder)
                .into(img.imgView)
    }


    /**
     * load cache image with Glide
     */
    private fun loadCache(ctx: Context, img: ImageLoader) {
        Glide.with(ctx).using(StreamModelLoader<String> { model, _, _ ->
            object : DataFetcher<InputStream> {
                @Throws(Exception::class)
                override fun loadData(priority: Priority): InputStream {
                    throw IOException()
                }

                override fun cleanup() {

                }

                override fun getId(): String {
                    return model
                }

                override fun cancel() {

                }
            }
        }).load(img.url).placeholder(img.placeHolder).diskCacheStrategy(DiskCacheStrategy.ALL).into(img.imgView)
    }
}