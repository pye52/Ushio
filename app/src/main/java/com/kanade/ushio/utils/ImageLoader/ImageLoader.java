package com.kanade.ushio.utils.ImageLoader;

import android.widget.ImageView;

import com.kanade.ushio.R;

public class ImageLoader {
    private int width;              // 加载宽度，需要与高度同时赋值，否则不处理
    private int height;             // 加载高度，需要与宽度同时赋值，否则不处理
    private String url;             // 需要解析的url
    private int placeHolder;        // 加载时显示的图片
    private int errorHolder;        // 当没有成功加载的时候显示的图片
    private ImageView imgView;      // ImageView的实例
    private int wifiStrategy;       // 加载策略，是否在wifi下才加载

    private ImageLoader(Builder builder) {
        this.width = builder.width;
        this.height = builder.height;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.errorHolder = builder.errorHolder;
        this.imgView = builder.imgView;
        this.wifiStrategy = builder.wifiStrategy;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getUrl() {
        return url;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getErrorHolder() {
        return errorHolder;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public int getWifiStrategy() {
        return wifiStrategy;
    }

    public static class Builder {
        private int width;
        private int height;
        private String url;
        private int placeHolder;
        private int errorHolder;
        private ImageView imgView;
        private int wifiStrategy;

        public Builder() {
            this.width = -1;
            this.height = -1;
            this.url = "";
            this.placeHolder = -1;
            this.errorHolder = R.drawable.img_on_error;
            this.imgView = null;
            this.wifiStrategy = ImageLoaderUtil.LOAD_STRATEGY_NORMAL;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeHolder(int placeHolder) {
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder errorHolder(int errorHolder) {
            this.errorHolder = errorHolder;
            return this;
        }

        public Builder imgView(ImageView imgView) {
            this.imgView = imgView;
            return this;
        }

        public Builder strategy(int strategy) {
            this.wifiStrategy = strategy;
            return this;
        }

        public ImageLoader build() {
            return new ImageLoader(this);
        }

    }
}
