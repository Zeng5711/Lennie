package com.hxyd.dyt.common.uitl;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hxyd.dyt.R;

/**
 * Created by win7 on 2017/3/11.
 */

public class ImageloadTools {

    public static void load(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.load).error(R.mipmap.load_err).into(imageView);
    }

    public static void load(Context context, Uri uri, ImageView imageView) {
        Glide.with(context).load(uri).placeholder(R.mipmap.load).error(R.mipmap.load_err).into(imageView);
    }

    public static void load(Context context, Bitmap bitmap, ImageView imageView) {
        Glide.with(context).load(bitmap).placeholder(R.mipmap.load).error(R.mipmap.load_err).into(imageView);
    }

    public static void load(Context context, int url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.load).error(R.mipmap.load_err).into(imageView);
    }

    public static void loadShape(Context context, String url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.load).error(R.mipmap.load_err).transform(new GlideRoundTransform(context)).into(imageView);
    }

    public static void loadShape(Context context, int url, ImageView imageView) {
        Glide.with(context).load(url).placeholder(R.mipmap.load).error(R.mipmap.load_err).transform(new GlideRoundTransform(context)).into(imageView);
    }
}
